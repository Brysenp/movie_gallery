package com.test.moviegallery.ui.movieDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.moviegallery.data.model.MovieDetails;
import com.test.moviegallery.repository.MovieRepository;
import com.test.moviegallery.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class MovieDetailViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    public LiveData<Boolean> loading = _loading;
    private MutableLiveData<MovieDetails> _data = new MutableLiveData<>();

    public LiveData<MovieDetails> data = _data;
    private  MovieRepository movieRepository;
    @Inject
    public MovieDetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void load(String movieId) {
        if (movieId.isEmpty()) {
            showToast("Something went wrong.");
            return;
        }

        _loading.postValue(true);

        Call<MovieDetails> call = movieRepository.getMovieDetail(movieId);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                _loading.postValue(false);
                if (!response.isSuccessful()) {
                    showToast(response.message());
                    return;
                }
                MovieDetails movie = response.body();
                _data.postValue(movie); // Update the LiveData with the retrieved movie details
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                _loading.postValue(false);
                showToast(t);
            }
        });
    }
}
