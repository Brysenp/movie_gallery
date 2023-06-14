package com.test.moviegallery.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieBaseResponse {

    @Expose
    @SerializedName("page")
    private Integer page;
    @Expose
    @SerializedName("results")
    private List<Movie> results;
    @Expose
    @SerializedName("total_pages")
    private Integer totalPages;
    @Expose
    @SerializedName("total_results")
    private Integer totalResults;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
