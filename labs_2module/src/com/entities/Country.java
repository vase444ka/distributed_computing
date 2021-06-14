package com.entities;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private long id;

    public Country(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private String name;
    private List<City> cities = new ArrayList<>();

    public Country() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
