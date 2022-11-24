package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Author;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {



    @LocalServerPort
    int port;

    @Test
    void getAllBooks() {

        List<BookDto> result=
        RestAssured.given().port(port)
                .when().get("/books")
                .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>(){}) ;
        assertEquals(4,result.size());
    }

    @Test
    void getBookById() {
        BookDto result =
                RestAssured.given().port(port)
                        .when().get("/books/1")
                        .then().statusCode(200).and().extract().as(BookDto.class);
        assertEquals("The Lord Of The Rings: The Return Of The King", result.title());
    }

    @Test
    void getBookByISBN() {
        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books?isbn=9780395647400")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });
        assertEquals("The Lord Of The Rings: The Return Of The King", result.get(0).title());

    }

    @Test
    void getBookByISBNWithRegex() {
        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books?isbn=9780*")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>(){});
        assertEquals(4,result.size());

    }

    @Test
    void getBookByTitle() {
        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books?title=The Lord Of The Rings: The Return Of The King")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });
        assertEquals("The Lord Of The Rings: The Return Of The King", result.get(0).title());
    }

    @Test
    void getBookByTitleWithRegex() {
        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books?title=*Lord*")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });
        assertEquals("The Lord Of The Rings: The Return Of The King", result.get(0).title());
    }

    @Test
    void getBookByTitleWith_whenNothingIsFound_thenResponseMessageIsReturned() {
        Map<String, String> response =
                RestAssured.given().port(port)
                        .when().get("/books?title=*545646545646")
                        .then().statusCode(400).extract().body().as(new TypeRef<Map<String, String>>() {
                        });

        String ResponseMessage = new JSONObject(response).get("message").toString();

        assertEquals("No book(s) matches for given (partial) title.", ResponseMessage);
    }

    @Test
    void createBook() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("Id", "4");
        requestParams.put("title", "Test");
        requestParams.put("description", "Testing testing");
        requestParams.put("isbn", "123456789");
        requestParams.put("author", "Tester Testington");

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().post("/books")
                        .then().statusCode(201).and().extract().as(BookDto.class);
        assertEquals("Test", result.title());
    }
    @Test
    void updateBookWithOneField(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("description", "Testing testing");
        requestParams.put("author", null);

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);
        assertEquals("Testing testing", result.description());
    }
    @Test
    void updateBookWithAllFields(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "Test");
        requestParams.put("description", "Testing testing");
        requestParams.put("author", new Author("Tester"," Testington"));

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);
        assertEquals("Test", result.title());

    }

}