package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Member;
import io.restassured.RestAssured;
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
        MemberDto result=
                RestAssured.given().port(port)
                        .when().post("/members")
                        .then().statusCode(201).and().extract().as(MemberDto.class);

    }
}