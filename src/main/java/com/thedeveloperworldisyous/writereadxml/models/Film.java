package com.thedeveloperworldisyous.writereadxml.models;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 8/10/15.
 */
public class Film {

    String title;
    String runningTime;
    String country;
    String director;
    List<Actor> cast;

    public Film(String title, String runningTime, String country, String director, List<Actor> cast) {
        this.title = title;
        this.runningTime = runningTime;
        this.country = country;
        this.director = director;
        this.cast = cast;
    }


    public Film(String title, String runningTime, String country, String director) {
        this.title = title;
        this.runningTime = runningTime;
        this.country = country;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }
}
