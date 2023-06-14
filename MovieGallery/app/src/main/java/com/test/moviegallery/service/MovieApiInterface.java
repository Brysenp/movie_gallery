package com.test.moviegallery.service;

import com.test.moviegallery.data.model.MovieBaseResponse;
import com.test.moviegallery.data.model.MovieDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {

    @GET("/3/movie/popular")
    Call<MovieBaseResponse> getPopularMovie(
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("/3/search/movie")
    Call<MovieBaseResponse> searchMovie(
            @Query("query") String query,
            @Query("include_adult") Boolean adult,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{movieId}?language=en-US")
    Call<MovieDetails> getMovieDetails(
            @Path("movieId") String id
    );

}
