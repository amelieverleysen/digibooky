package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;

public record MemberAdminDto(String id, String name, String surname, String email,
                             String street, String houseNumber, City city) {
}
