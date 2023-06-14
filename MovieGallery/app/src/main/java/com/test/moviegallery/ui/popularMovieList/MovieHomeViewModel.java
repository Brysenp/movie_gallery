package com.test.moviegallery.ui.popularMovieList;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.moviegallery.data.model.MovieBaseResponse;
import com.test.moviegallery.data.model.MovieMapper;
import com.test.moviegallery.repository.MovieRepository;
import com.test.moviegallery.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class MovieHomeViewModel extends BaseViewModel {

    @Inject
    public MovieHomeViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    private  MovieRepository movieRepository;
    private MutableLiveData<List<Model>> _data = new MutableLiveData<>(Collections.emptyList());
    public LiveData<List<Model>> data = _data;

    private String query = "";
    private int page = 1;
    private boolean isLoading = false;
    private int totalResults = 0;

    public void search(String query) {
        page = 1;
        isLoading = true;
        insertLoading(true);

        // If query is empty, continue pagination for the searched keyword
        if (query.isEmpty()){
            getPopularMovieList(page);
        }else{
            searchMovieList(query, page);
        }

        this.query = query;

    }

    public void load() {
        if (isLoading) return;

        if (data.getValue() != null) {
            int count = data.getValue().size();
            if (count >= totalResults) return;
        }

        page += 1;
        isLoading = true;
        insertLoading(false);
        if(!this.query.isEmpty()){
            searchMovieList(this.query, page);
        }else{
            getPopularMovieList(page);

        }
    }

    public boolean isLastItemShown(int row) {
        List<Model> value = data.getValue();
        return value != null && row == value.size() - 1;
    }

    private void processOnResponse(Response<MovieBaseResponse> response){
        isLoading = false;

        if (!response.isSuccessful()) {
            showToast(response.message());
            return;
        }

        totalResults = response.body() != null ? response.body().getTotalResults().intValue() : 0;

        List<Model> oldData = data.getValue();
        if (oldData != null) {
            if (oldData.get(oldData.size() - 1).getType() == MovieHomeAdapter.Type.LOADING) {
                oldData = oldData.subList(0, oldData.size() - 1);
            }
        }

        List<Model> newData = response.body() != null ? MovieMapper.mapModel(response.body().getResults()) : Collections.emptyList();

        List<Model> mergedData = new ArrayList<>();
        if (oldData != null) {
            mergedData.addAll(oldData);
        }
        mergedData.addAll(newData);

        _data.postValue(mergedData);
    }

    private void processOnFailure(Throwable t){
        isLoading = false;

        List<Model> oldData = data.getValue();
        if (oldData != null && oldData.get(oldData.size() - 1).getType() == MovieHomeAdapter.Type.LOADING) {
            oldData = oldData.subList(0, oldData.size() - 1);
        }

        _data.postValue(oldData != null ? oldData : Collections.emptyList());

        showToast(t);
    }

    private void getPopularMovieList(int page) {
        Call<MovieBaseResponse> call = movieRepository.getPopularMovie(page);
        call.enqueue(new Callback<MovieBaseResponse>() {
            @Override
            public void onResponse(Call<MovieBaseResponse> call, Response<MovieBaseResponse> response) {
                processOnResponse(response);
            }

            @Override
            public void onFailure(Call<MovieBaseResponse> call, Throwable t) {
                processOnFailure(t);
            }
        });
    }
    private void searchMovieList(String query, int page) {
        Call<MovieBaseResponse> call = movieRepository.searchMovie(query, page);
        call.enqueue(new Callback<MovieBaseResponse>() {
            @Override
            public void onResponse(Call<MovieBaseResponse> call, Response<MovieBaseResponse> response) {
                processOnResponse(response);
            }

            @Override
            public void onFailure(Call<MovieBaseResponse> call, Throwable t) {
                processOnFailure(t);
            }
        });
    }

    private void insertLoading(boolean clear) {
        List<Model> currentData = data.getValue();
        if (clear) {
            _data.postValue(Collections.singletonList(new LoadingModel()));
        } else {
            if (currentData != null && currentData.get(currentData.size() - 1).getType() == MovieHomeAdapter.Type.LOADING) {
                return;
            }

            List<Model> newData = currentData != null ? new ArrayList<>(currentData) : new ArrayList<>();
            newData.add(new LoadingModel());

            _data.postValue(newData);
        }
    }

}
