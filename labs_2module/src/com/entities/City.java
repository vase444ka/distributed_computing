package com.entities;

public class City {
    public City(long id, long countryId, String name, long population) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
        this.population = population;
    }

    public City() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    private long id;
    private long countryId;
    private String name;
    private long population;

}
