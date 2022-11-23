package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;

public class MemberDto {
    private String id;
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

    public MemberDto setName(String name) {
        this.name = name;
        return this;
    }

    public MemberDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public MemberDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberDto setINSS(String INSS) {
        this.INSS = INSS;
        return this;
    }

    public MemberDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public MemberDto setHousenumber(String housenumber) {
        this.housenumber = housenumber;
        return this;
    }

    public MemberDto setCity(City city) {
        this.city = city;
        return this;
    }

    public String getId() {
        return id;
    }

    public MemberDto setId(String id) {
        this.id = id;
        return this;
    }
}
