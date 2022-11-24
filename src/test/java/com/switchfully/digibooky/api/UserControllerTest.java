package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.City;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.security.Role;
import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    int port;

    @Test
    //DEZE TEST WERKT NIET!
    void createMember(){
        //String id, String name, String surname, String email, Role role, String password, String INSS, String street, String housenumber, City city
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

        MemberDto result=
                RestAssured.given().port(port).contentType("application/json").body(requestParams)
                        .when().post("/users/member")
                        .then().statusCode(201).and().extract().as(MemberDto.class);
        assertEquals("test@test.com", result.email());
    }
}