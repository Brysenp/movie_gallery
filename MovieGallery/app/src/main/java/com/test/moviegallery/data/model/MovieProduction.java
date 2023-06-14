package com.test.moviegallery.data.model;

import com.google.gson.annotations.SerializedName;

public class MovieProduction {

    @SerializedName("logo_path")
    private String logo;
    @SerializedName("name")
    private String name;
    @SerializedName("origin_country")
    private String country;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
