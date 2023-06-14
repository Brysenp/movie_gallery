package com.test.moviegallery.ui.popularMovieList;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

// Models
public class MovieModel implements Model {
    private String id;
    private String title;
    private String year;
    private String posterUrl;

    private Double voteAvg;
    private List<String> genresId;
    private MovieHomeAdapter.Type type;

    public MovieModel(String id, String title, String year, String posterUrl, Double voteAvg, List<String> genresId) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.voteAvg = voteAvg;
        this.genresId = genresId;
        this.type = MovieHomeAdapter.Type.DATA;
    }

    @Override
    public MovieHomeAdapter.Type getType() {
        return type;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getYear() {
        try{
            LocalDate date = LocalDate.parse(year);
            int inYear = date.getYear();
            return Integer.toString(inYear);

        }catch(DateTimeParseException e){
            Log.e(TAG, "getYear: "+e);
            return "Not Available";
        }
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public List<String> getGenresId() {
        return genresId;
    }

    public void setGenresId(List<String> genresId) {
        this.genresId = genresId;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }

}
