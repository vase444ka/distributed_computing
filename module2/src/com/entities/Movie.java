package com.entities;

import java.util.Date;
import java.util.List;

public class Movie {
    private Artist director;
    private List<Artist> actors;
    private String title;
    private String country;
    private Date released;

    public Movie(Artist director, List<Artist> actors, String country, String title, Date released) {
        this.director = director;
        this.actors = actors;
        this.country = country;
        this.title = title;
        this.released = released;
    }

    public Artist getDirector() {
        return director;
    }

    public List<Artist> getActors() {
        return actors;
    }

    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleased() {
        return released;
    }

    @Override
    public String toString() {
        return title + '\'' +
                ", directed by" + director +
                ", starring " + actors +
                ", originCountry: " + country + '\'' +
                ", released=" + released;
    }
}
