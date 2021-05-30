package com.entities;


import java.io.Serializable;
import java.sql.Date;
import java.util.Scanner;

public class Artist implements Serializable {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    public Artist(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public static Artist read(){
        Scanner scanner = new Scanner(System.in);
        String fname, lname;
        Date dateofBirth;
        System.out.println("Enter new artist's data:");
        System.out.println("First name: ");
        fname = scanner.nextLine();
        System.out.println("Last name: ");
        lname = scanner.nextLine();
        System.out.println("Date of birth in YYYY-MM-DD format: ");
        dateofBirth = Date.valueOf(scanner.nextLine());
        return new Artist(fname, lname, dateofBirth);
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
                ", born " + dateOfBirth;
    }
}
