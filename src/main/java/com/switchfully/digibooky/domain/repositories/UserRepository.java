package com.switchfully.digibooky.domain.repositories;

import com.google.common.collect.ImmutableMap;
import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.security.Role;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private Map<String, User> userMap = ImmutableMap.<String, User>builder()
            .put("Member", new Member("Member", "Kurt", "Kurtsen", "kurt@gmail.com", Role.MEMBER, "pwd",  "90.02.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen")))
            .put("Librarian", new User("Librarian", "Bob", "Bobbet", "bob@gmail.com", Role.LIBRARIAN, "pwd"))
            .put("Admin", new User("Admin", "Jos", "Josset", "jos@gmail.com", Role.ADMIN, "pwd"))
            .build();

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(userMap.get(username));
    }
}
