package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.api.UserController;
import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    Logger myLogger = LoggerFactory.getLogger(UserController.class);
    private final Map<String, User> userMap = new HashMap<>();

    public UserRepository() {
        this.userMap.put("1", new Member("Member", "Kurt", "Kurtsen", "kurt@gmail.com", Role.MEMBER, "pwd",  "90.02.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen")));
        this.userMap.put("2", new User("Librarian", "Bob", "Bobbet", "bob@gmail.com", Role.LIBRARIAN, "pwd"));
        this.userMap.put("3", new User("Admin", "Jos", "Josset", "jos@gmail.com", Role.ADMIN, "pwd"));
        this.userMap.put("4", new Member("Member2", "Bert", "Bertsen", "bert@gmail.com", Role.MEMBER, "pwd",  "90.02.01-997-05", "Bertstraaat", "15", new City("2000", "Antwerpen")));
    }

    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    public Member save(Member member) throws IllegalArgumentException{
        if (userMap.containsValue(member)) throw new IllegalArgumentException(member.getName() + " " + member.getSurname() + " already exists.");

        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getEmail().equals(member.getEmail())) throw new IllegalArgumentException("This email adress already exists.");
            if (entry.getValue() instanceof Member && ((Member) entry.getValue()).getInss().equals(member.getInss())) throw new IllegalArgumentException("This inss already exists.");
        }

        userMap.put(member.getId(), member);
        myLogger.info(member.getName() + " " + member.getSurname() + " is created.");
        return member;
    }

    public User save(User user) throws  IllegalArgumentException{
        if (userMap.containsValue(user)) throw new IllegalArgumentException(user.getName() + " " + user.getSurname() + " already exists.");

        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getEmail().equals(user.getEmail()))
                throw new IllegalArgumentException("This email adress already exists.");
        }
        userMap.put(user.getId(), user);
        myLogger.info(user.getName() + " " + user.getSurname() + " is created.");
        return user;
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
