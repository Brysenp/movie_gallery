package com.test.moviegallery.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("original_title")
    private String title;
    @SerializedName("production_companies")
    private List<MovieProduction> productionList;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterUrl;
    @SerializedName("status")
    private String status;
    @SerializedName("overview")
    private String overview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieProduction> getProductionList() {
        return productionList;
    }

    public void setProductionList(List<MovieProduction> productionList) {
        this.productionList = productionList;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
