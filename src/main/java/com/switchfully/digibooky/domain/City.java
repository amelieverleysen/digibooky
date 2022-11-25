package com.switchfully.digibooky.domain;

import java.util.Objects;

public class City {
    private String postalCode;
    private String cityName;

    public City(String postalCode, String cityName) {
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(postalCode, city.postalCode) && Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, cityName);
    }
}
