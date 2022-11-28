package com.switchfully.digibooky.domain;

import java.util.Objects;

public record City(String postalCode, String cityName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(postalCode, city.postalCode) && Objects.equals(cityName, city.cityName);
    }

}
