package com.switchfully.digibooky.domain;

public class LendInfoBook {
    private String firstname;
    private String lastname;
    private final String IsLended;

    public LendInfoBook(String isLended, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.IsLended = isLended;
    }

    public String getIsLended() {
        return IsLended;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}


