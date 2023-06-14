package com.test.moviegallery.ui.popularMovieList;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.test.moviegallery.R;
import com.test.moviegallery.data.constant.ConstData;
import com.test.moviegallery.databinding.CellLoadingBinding;
import com.test.moviegallery.databinding.CellMovieListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MovieHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Model> data = new ArrayList<>();
    private Consumer<String> onTap;

    public MovieHomeAdapter(Context context, Consumer<String> onTap) {
        this.context = context;
        this.onTap = onTap;
    }

    public void setData(List<Model> value) {
        DiffCallback diffCallback = new DiffCallback(data, value);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        data = value;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 1:
                CellLoadingBinding loadingBinding = DataBindingUtil.inflate(inflater, R.layout.loading_list_item, parent, false);
                return new LoadingViewHolder(loadingBinding);
            default:
                CellMovieListBinding movieListBinding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item, parent, false);
                return new MovieViewHolder(movieListBinding, onTap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Use Model to determine which ViewHolder to render
        switch (holder.getItemViewType()) {
            case 0:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.bind(context, (MovieModel) data.get(position));
                break;
            case 1:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType().getViewType();
    }

    // View Holder
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private CellMovieListBinding binding;
        private Consumer<String> onTap;

        MovieViewHolder(CellMovieListBinding binding, Consumer<String>  onTap) {
            super(binding.getRoot());
            this.binding = binding;
            this.onTap = onTap;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(Context context, MovieModel movieModel) {
            binding.movieTitle.setText(movieModel.getTitle() + " ("+movieModel.getYear()+")");
            binding.movieYear.setText("Release In"+movieModel.getYear());
            binding.rate.setText("Average Score "+movieModel.getVoteAvg().toString());

            Glide.with(context)
                    .load(ConstData.imagePath+movieModel.getPosterUrl())
                    .transform(new CenterInside(), new RoundedCorners(24))
                    .centerCrop()
                    .placeholder(R.color.colorBlack)
                    .into(binding.moviePoster);



            binding.listView.setOnClickListener(view -> onTap.accept(movieModel.getId()));

        }
    }

    // View Holder
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private CellLoadingBinding binding;

        LoadingViewHolder(CellLoadingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            // Implement if needed
        }
    }

    // ViewType enums
    enum Type {
        DATA(0),
        LOADING(1);

        private int viewType;

        Type(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }
    static class DiffCallback extends DiffUtil.Callback {
        private List<Model> oldList;
        private List<Model> newList;

        DiffCallback(List<Model> oldList, List<Model> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Model oldItem = oldList.get(oldItemPosition);
            Model newItem = newList.get(newItemPosition);

            if (oldItem.getType() == Type.LOADING && newItem.getType() == Type.LOADING) return true;

            if (oldItem instanceof MovieModel && newItem instanceof MovieModel) {
                MovieModel oldMovieModel = (MovieModel) oldItem;
                MovieModel newMovieModel = (MovieModel) newItem;

                return oldMovieModel.getId().equals(newMovieModel.getId());
            }

            return false;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Model oldItem = oldList.get(oldItemPosition);
            Model newItem = newList.get(newItemPosition);

            if (oldItem instanceof LoadingModel && newItem instanceof LoadingModel) {
                return true;
            }

            if (oldItem instanceof MovieModel && newItem instanceof MovieModel) {
                MovieModel oldMovieModel = (MovieModel) oldItem;
                MovieModel newMovieModel = (MovieModel) newItem;

                return oldMovieModel.equals(newMovieModel);
            }

            return false;
        }
    }

}

