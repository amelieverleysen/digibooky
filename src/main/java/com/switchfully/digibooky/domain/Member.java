package com.switchfully.digibooky.domain;

import com.switchfully.digibooky.domain.security.Role;

import java.util.Objects;

public class Member extends User {
    private final String INSS;
    private String street;
    private String housenumber;
    private City city;

    public Member(String name, String surname, String email, Role role, String password, String INSS, String street, String housenumber, City city) {
        super(name, surname, email, role, password);
        this.INSS = INSS;
        this.street = street;
        this.housenumber = housenumber;
        this.city = city;
    }

    public Member(String id, String name, String surname, String email, Role role, String password, String INSS, String street, String housenumber, City city) {
        super(id, name, surname, email, role, password);
        this.INSS = INSS;
        this.street = street;
        this.housenumber = housenumber;
        this.city = city;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return Objects.equals(INSS, member.INSS) && Objects.equals(street, member.street) && Objects.equals(housenumber, member.housenumber) && Objects.equals(city, member.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), INSS, street, housenumber, city);
    }
}
