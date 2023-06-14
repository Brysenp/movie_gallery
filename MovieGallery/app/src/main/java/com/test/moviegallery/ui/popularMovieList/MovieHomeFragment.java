package com.test.moviegallery.ui.popularMovieList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.moviegallery.R;
import com.test.moviegallery.databinding.FragmentMovieListBinding;
import com.test.moviegallery.ui.base.BaseFragment;
import com.test.moviegallery.ui.movieDetail.MovieDetailFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieHomeFragment extends BaseFragment<MovieHomeViewModel> {

    private FragmentMovieListBinding binding;
    private MovieHomeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_list;
    }

    @Override
    protected MovieHomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(MovieHomeViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        setupObservers();
        load();
    }


    @Override
    public void onDestroy() {
        removeObservers();
        super.onDestroy();
    }

    private void setupView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return true;}

            @Override
            public boolean onQueryTextSubmit(String query) {
                getViewModel().search(query != null ? query : "");
                return true;
            }
        });

        adapter = new MovieHomeAdapter(requireContext(), movieId -> {
            Bundle bundle = new Bundle();
            bundle.putString(MovieDetailFragment.ARG_IMDB_ID, movieId);
            NavController navController = NavHostFragment.findNavController(MovieHomeFragment.this);
            navController.navigate(R.id.navMovieListToMovieDetail, bundle);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (getViewModel().isLastItemShown(lastVisibleItemPosition)) {
                        getViewModel().load();
                    }
                }
            }
        });
    }

    private void setupObservers() {
        getViewModel().data.observe(getViewLifecycleOwner(), data -> adapter.setData(data));
    }

    private void load() {
        getViewModel().search("");
    }

    private void removeObservers() {
        getViewModel().data.removeObservers(this);
    }
}