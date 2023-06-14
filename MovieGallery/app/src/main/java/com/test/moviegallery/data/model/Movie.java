package com.test.moviegallery.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("id")
    private String id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String posterUrl;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<String> genres;

    public Movie(String id, String title, String posterUrl, Double voteAverage, String releaseDate, List<String> genres) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}


