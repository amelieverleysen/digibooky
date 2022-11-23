package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.security.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private Map<String, User> userMap = new HashMap<>();

    public UserRepository() {
        this.userMap.put("Member", new Member("Member", "Kurt", "Kurtsen", "kurt@gmail.com", Role.MEMBER, "pwd",  "90.02.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen")));
        this.userMap.put("Librarian", new User("Librarian", "Bob", "Bobbet", "bob@gmail.com", Role.LIBRARIAN, "pwd"));
        this.userMap.put("Admin", new User("Admin", "Jos", "Josset", "jos@gmail.com", Role.ADMIN, "pwd"));
    }

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(userMap.get(username));
    }

    public Member save(Member member) throws IllegalArgumentException{
        if (userMap.containsValue(member)) throw new IllegalArgumentException(member.getName() + " " + member.getSurname() + " already exists.");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getEmail().equals(member.getEmail())) throw new IllegalArgumentException("This emailadress already exists.");
        }
        userMap.put(member.getId(), member);
        return member;
    }


}
