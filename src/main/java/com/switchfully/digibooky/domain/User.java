package com.switchfully.digibooky.domain;

import com.switchfully.digibooky.domain.security.Role;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String id;
    private String name;
    private String surname;
    private String email;
    private Role role;
    private String password;

    public User(String name, String surname, String email, Role role, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User(String id, String name, String surname, String email, Role role, String password) {
        new User(name, surname, email, role, password);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email);
    }
}
