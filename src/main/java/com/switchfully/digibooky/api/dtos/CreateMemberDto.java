package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;

public record CreateMemberDto(String name, String surname, String email, String password, String inss, String street, String housenumber, City city) {
}
