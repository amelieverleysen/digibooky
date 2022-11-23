package com.switchfully.digibooky.domain;

import com.switchfully.digibooky.domain.security.Role;

import java.util.Objects;

public class Member extends User {
    private final String Inss;
    private String street;
    private String housenumber;
    private City city;

    public Member(String name, String surname, String email, Role role, String password, String INSS, String street, String housenumber, City city) {
        super(name, surname, email, role, password);
        this.Inss = INSS;
        this.street = street;
        this.housenumber = housenumber;
        this.city = city;
    }

    public Member(String id, String name, String surname, String email, Role role, String password, String INSS, String street, String housenumber, City city) {
        super(id, name, surname, email, role, password);
        this.Inss = INSS;
        this.street = street;
        this.housenumber = housenumber;
        this.city = city;
    }

    public String getInss() {
        return Inss;
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
        return Objects.equals(Inss, member.Inss) && Objects.equals(street, member.street) && Objects.equals(housenumber, member.housenumber) && Objects.equals(city, member.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Inss, street, housenumber, city);
    }
}
