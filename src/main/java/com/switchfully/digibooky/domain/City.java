package com.switchfully.digibooky.domain;

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
}
