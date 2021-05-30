package com.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Movie implements Serializable {
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

    public static Movie read(){
        Scanner scanner = new Scanner(System.in);
        String title, country;
        Date released;
        System.out.println("Enter new movie's data:");
        System.out.println("Title: ");
        title = scanner.nextLine();
        System.out.println("Director: ");
        Artist director = Artist.read();
        System.out.println("Amount of actors: ");
        int N = new Scanner(System.in).nextInt();
        List <Artist> actors = new LinkedList<>();
        for (int i = 0; i < N; i++){
            actors.add(Artist.read());
        }
        System.out.println("Origin country: ");
        country = scanner.nextLine();
        System.out.println("Release date in YYYY-MM-DD format: ");
        released = Date.valueOf(scanner.nextLine());
        return new Movie(director, actors, country, title, released);
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
