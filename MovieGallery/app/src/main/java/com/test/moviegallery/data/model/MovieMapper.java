package com.test.moviegallery.data.model;

import com.test.moviegallery.ui.popularMovieList.Model;
import com.test.moviegallery.ui.popularMovieList.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<Model> mapModel(List<Movie> movies) {
        List<Model> list = new ArrayList<>();

        for (Movie movie : movies) {
                list.add(new MovieModel(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getReleaseDate(),
                        movie.getPosterUrl(),
                        movie.getVoteAverage(),
                        movie.getGenres()
                ));
        }

        return list;
    }
}
