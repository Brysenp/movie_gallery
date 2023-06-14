package com.test.moviegallery.repository;

import com.test.moviegallery.data.model.MovieBaseResponse;
import com.test.moviegallery.data.model.MovieDetails;

import retrofit2.Call;

public interface MovieRepository {
    Call<MovieBaseResponse> getPopularMovie(Integer page);
    Call<MovieBaseResponse> searchMovie(String search, Integer page);

    Call<MovieDetails> getMovieDetail(String id);
}

