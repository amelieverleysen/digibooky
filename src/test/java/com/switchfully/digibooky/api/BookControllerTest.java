package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(3,result.size());
    }
    @Test
    void getBookById(){
        BookDto result=
                RestAssured.given().port(port)
                        .when().get("/books/1")
                        .then().statusCode(200).and().extract().as(BookDto.class);
        assertEquals("The Lord Of The Rings: The Return Of The King",result.title());
    }
    @Test
    void getBookByISBN(){
        List<BookDto> result=
                RestAssured.given().port(port)
                        .when().get("/books?isbn=9780395647400")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>(){});
        assertEquals("The Lord Of The Rings: The Return Of The King",result.get(0).title());

    }
    @Test
    void getBookByISBNWithRegex(){
        List<BookDto> result=
                RestAssured.given().port(port)
                        .when().get("/books?isbn=9780*")
                        .then().statusCode(200).and().extract().as(new TypeRef<List<BookDto>>(){});
        assertEquals(3,result.size());

    }

}