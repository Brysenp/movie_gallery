package com.test.moviegallery.ui.popularMovieList;

public class LoadingModel implements Model {
    private MovieHomeAdapter.Type type;

    public LoadingModel() {
        this.type = MovieHomeAdapter.Type.LOADING;
    }

    @Override
    public MovieHomeAdapter.Type getType() {
        return type;
    }
}
