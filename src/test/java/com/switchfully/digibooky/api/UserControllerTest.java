package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.domain.security.Role;
import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {


    @Autowired
    private UserRepository users;

    @LocalServerPort
    int port;



    @Test
    void createMember() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("Id", "1");
        requestParams.put("name", "Test");
        requestParams.put("surname", "Tester");
        requestParams.put("email", "test@test.com");
        requestParams.put("role", Role.MEMBER);
        requestParams.put("password", "pwd");
        requestParams.put("inss", "1234");
        requestParams.put("street", "teststraat");
        requestParams.put("housenumber", "1");
        requestParams.put("city", new City("3000", "Leuven"));

        MemberDto result =
                RestAssured.given().port(port).contentType("application/json").body(requestParams)
                        .when().post("/users/member")
                        .then().statusCode(201).and().extract().as(MemberDto.class);
        assertEquals("test@test.com", result.email());
    }
    @Test
    void createLibrarian(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("Id","2");
        requestParams.put("name","Test");
        requestParams.put("surname", "Tester");
        requestParams.put("email", "test2@test.com");
        requestParams.put("role", Role.LIBRARIAN);
        requestParams.put("password", "pwd");

        UserDto result=
                RestAssured.given().port(port).auth().preemptive().basic("3", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().post("/users/librarian")
                        .then().statusCode(201).and().extract().as(UserDto.class);
        assertEquals("test2@test.com",result.email());
    }
}