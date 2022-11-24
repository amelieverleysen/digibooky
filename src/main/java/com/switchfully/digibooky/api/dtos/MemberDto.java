package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;

public record MemberDto(String id, String name, String surname, String email, String INSS,
                        String street, String housenumber, City city) {
}
