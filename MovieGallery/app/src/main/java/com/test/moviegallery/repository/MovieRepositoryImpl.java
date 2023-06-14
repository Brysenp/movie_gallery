package com.test.moviegallery.repository;

import com.test.moviegallery.data.model.MovieBaseResponse;
import com.test.moviegallery.data.model.MovieDetails;
import com.test.moviegallery.service.MovieApiInterface;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieApiInterface movieApiInterface;

    @Inject
    public MovieRepositoryImpl(MovieApiInterface movieApiInterface) {
        this.movieApiInterface = movieApiInterface;
    }

    @Override
    public Call<MovieBaseResponse> getPopularMovie(Integer page) {
        Call<MovieBaseResponse> response = movieApiInterface.getPopularMovie("en-US", page);
        return response;
    }

    @Override
    public Call<MovieBaseResponse> searchMovie(String search, Integer page){
        Call<MovieBaseResponse> response = movieApiInterface.searchMovie(search, false, "en-US", page);
        return response;
    }

    @Override
    public Call<MovieDetails> getMovieDetail(String id){
        Call<MovieDetails> response = movieApiInterface.getMovieDetails(id);
        return response;
    }

}
