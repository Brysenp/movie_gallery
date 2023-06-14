package com.test.moviegallery.ui.movieDetail;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.test.moviegallery.R;
import com.test.moviegallery.data.constant.ConstData;
import com.test.moviegallery.data.model.MovieProduction;
import com.test.moviegallery.databinding.FragmentMovieDetailBinding;
import com.test.moviegallery.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class MovieDetailFragment extends BaseFragment<MovieDetailViewModel> {

    public static final String ARG_IMDB_ID = "ARG_IMDB_ID";
    private FragmentMovieDetailBinding binding;
    private String movieId = "";


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    protected MovieDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(MovieDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        binding.setLifecycleOwner(this);
        movieId = (String) getArguments().get(ARG_IMDB_ID);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        setupObservers();
        loadData();
    }

    @Override
    public void onDestroy() {
        removeObservers();
        super.onDestroy();
    }

    private void setupView() {
        binding.btnBack.setOnClickListener(v -> NavHostFragment.findNavController(MovieDetailFragment.this).popBackStack());
    }

    // Setup observer, if there's changes on LiveData, the data will updated the component
    private void setupObservers() {
        getViewModel().loading.observe(getViewLifecycleOwner(), loading -> {
            if (loading) {
                binding.progressBar.setVisibility(VISIBLE);
            } else {
                binding.progressBar.setVisibility(GONE);
            }
        });

        getViewModel().data.observe(getViewLifecycleOwner(), movieDetails -> {
            binding.title.setText(movieDetails.getTitle());
            binding.desc.setText(movieDetails.getOverview());
            binding.release.setText(movieDetails.getReleaseDate());
            Glide.with(requireContext())
                    .load(ConstData.imagePath + movieDetails.getPosterUrl())
                    .transform(new CenterInside(), new RoundedCorners(24))
                    .centerCrop()
                    .placeholder(R.color.colorBlack)
                    .into(binding.poster);

            if(movieDetails.getProductionList().get(0) != null){
                MovieProduction production = movieDetails.getProductionList().get(0);
                binding.prodName.setText(movieDetails.getProductionList().get(0).getName());
                Glide.with(requireContext())
                        .load(ConstData.imagePath + production.getLogo())
                        .centerCrop()
                        .placeholder(R.color.colorBlack)
                        .into(binding.prodLogo);
            }
        });
    }

    // Load data when view is created
    private void loadData() {
        getViewModel().load(movieId);
    }

    // Remove observer that use in this fragment, to avoid memory leak
    private void removeObservers() {
        getViewModel().data.removeObservers(this);
        getViewModel().loading.removeObservers(this);
    }
}