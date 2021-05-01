package com.entities;


import java.sql.Date;

public class Artist {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    public Artist(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName +
                ", born" + dateOfBirth;
    }
}
