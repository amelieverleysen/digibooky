package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Author;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerTest {


    @LocalServerPort
    int port;

    @Test
    void getAllBooks() {

        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals(4, result.size());
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
                        .when().get("/books?isbn=9780435123987")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals("Mathilda", result.get(0).title());
    }

    @Test
    void getBookByISBNWithRegex() {
        List<BookDto> result =
                RestAssured.given().port(port)
                        .when().get("/books?isbn=*435123987")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals("Mathilda", result.get(0).title());
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
                        .when().get("/books?title=*Lord*Tower*")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals("The Lord Of The Rings: The Two Towers", result.get(0).title());
    }

    @Test
    void getBookByTitle_whenNothingIsFound_thenResponseMessageIsReturned() {
        Map<String, String> response =
                RestAssured.given().port(port)
                        .when().get("/books?title=*545646545646")
                        .then().statusCode(404).extract().body().as(new TypeRef<Map<String, String>>() {
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
        requestParams.put("author", new Author("test", "test"));

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().post("/books")
                        .then().statusCode(201).and().extract().as(BookDto.class);

        assertEquals("Test", result.title());
    }

    @Test
    void updateBookWithOneField() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("description", "Testing testing");
        requestParams.put("author", null);
        requestParams.put("isDeleted", false);

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);

        assertEquals("Testing testing", result.description());
    }

    @Test
    void updateBookWithAllFields() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "Test");
        requestParams.put("description", "Testing testing");
        requestParams.put("author", new Author("Tester", " Testington"));
        requestParams.put("isDeleted", false);

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);

        assertEquals("Test", result.title());
    }

    @Test
    void updateBookAuthorOnlyFirstName() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("description", "");
        requestParams.put("author", new Author("Tester", ""));
        requestParams.put("isDeleted", false);

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);


        assertEquals("Tester", result.author().getFirstname());
    }

    @Test
    void updateBookAuthorOnlyLastName() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("description", "");
        requestParams.put("author", new Author("", "Testington"));
        requestParams.put("isDeleted", false);

        BookDto result =
                RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json").body(requestParams)
                        .when().put("/books/1")
                        .then().statusCode(201).and().extract().as(BookDto.class);

        assertEquals("Testington", result.author().getLastname());
    }

    @Test
    void deleteBook() {
        RestAssured.given().port(port).auth().preemptive().basic("2", "pwd")
                .when().delete("/books/1");


        RestAssured.given().port(port).auth().preemptive().basic("2", "pwd")
                .when().get("/books/1")
                .then().assertThat().statusCode(404);
    }

    @Test
    void whenDeleteBookAndGetAllBooks_thenDeletedBookNotReturned() {

        RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json")
                .when().delete("/books/1")
                .then().statusCode(202).and().extract().as(BookDto.class);


        List<BookDto> result2 =
                RestAssured.given().port(port)
                        .when().get("/books")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals(3, result2.size());
    }

    @Test
    void whenDeleteBookAndGetBookById_thenDeletedBookNotReturned() {

        RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json")
                .when().delete("/books/1")
                .then().statusCode(202).and().extract().as(BookDto.class);


        Map<String, String> response =
                RestAssured.given().port(port)
                        .when().get("/books/1")
                        .then().statusCode(404).extract().body().as(new TypeRef<Map<String, String>>() {
                        });

        String ResponseMessage = new JSONObject(response).get("message").toString();

        assertEquals("No book with id: 1 in our book database.", ResponseMessage);
    }

    @Test
    void getBookByAuthor() {
        List<BookDto> result =
                RestAssured.given().port(port).contentType(ContentType.JSON).with().
                        queryParam("firstname", "Roald").
                        queryParam("lastname", "Dahl").
                        when().get("/books").then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals("Mathilda", result.get(0).title());
    }

    @Test
    void getBookByAuthorWithRegex() {
        List<BookDto> result =
                RestAssured.given().port(port).contentType(ContentType.JSON).with().
                        queryParam("firstname", "*ald").
                        queryParam("lastname", "Da?l").
                        when().get("/books").then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>() {
                        });

        assertEquals("Mathilda", result.get(0).title());
    }

    @Test
    void getBookByAuthor_whenNothingIsFound_thenResponseMessageIsReturned() {
        Map<String, String> response =
                RestAssured.given().port(port).contentType(ContentType.JSON).with().
                        queryParam("firstname", "Robert").
                        queryParam("lastname", "Jordan").
                        when().get("/books").then().statusCode(404).and().extract().
                        body().as(new TypeRef<Map<String, String>>() {
                        });

        String ResponseMessage = new JSONObject(response).get("message").toString();

        assertEquals("No book(s) matches for given (partial) authors first- or lastname.", ResponseMessage);
    }
    @AfterEach
    void cleanup(){
        RestAssured.reset();
    }
}