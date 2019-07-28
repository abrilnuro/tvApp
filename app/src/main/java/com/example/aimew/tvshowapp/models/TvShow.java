package com.example.aimew.tvshowapp.models;

import com.example.aimew.tvshowapp.utils.JSONKeys;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class TvShow implements Serializable {

    @SerializedName(JSONKeys.ID)
    private Integer id;

    @SerializedName(JSONKeys.NAME)
    private String name;

    @SerializedName(JSONKeys.LANGUAGE)
    private String language;

    @SerializedName(JSONKeys.GENRE)
    private String genre;

    @SerializedName(JSONKeys.RESUME)
    private String resume;

    @SerializedName(JSONKeys.POSTER)
    private String poster;

    @SerializedName(JSONKeys.RELEASE_DATE)
    private String releaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

