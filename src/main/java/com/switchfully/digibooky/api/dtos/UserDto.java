package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.security.Role;

public record UserDto (String id, String name, String surname, String email, Role role, String password){
}
