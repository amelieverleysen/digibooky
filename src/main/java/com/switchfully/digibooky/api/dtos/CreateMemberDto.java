package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;

public class CreateMemberDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String INSS;
    private String street;
    private String housenumber;
    private City city;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getINSS() {
        return INSS;
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public City getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setINSS(String INSS) {
        this.INSS = INSS;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
