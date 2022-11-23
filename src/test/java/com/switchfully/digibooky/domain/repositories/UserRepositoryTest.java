package com.switchfully.digibooky.domain.repositories;

import com.google.common.collect.ImmutableMap;
import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.security.Role;
import com.switchfully.digibooky.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();
    private Member memberToSave;
    private Map<String, User> testUserMap;
    @BeforeEach
    void initTestData(){
        memberToSave = new Member("Test", "Tester", "test@gmail.com", Role.MEMBER, "pwd",  "90.03.01-997-04", "Kurtstraaat", "25", new City("2000", "Antwerpen"));
    }

    @Test
    void whenCreateNewUser_userIsAddedToRepository() {
        //GIVEN WHEN
        Member savedMember = userRepository.save(memberToSave);

        //THEN
        Assertions.assertEquals(memberToSave, savedMember);
    }
}