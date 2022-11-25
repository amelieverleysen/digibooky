package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LentBookMemberTest {
    @LocalServerPort
    int port;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Tests regarding LendItem lists for members")
    @Nested
    class lentBooksMemberTest {
        @Test
        void whenMemberLoansBook_LibrarianAsksLendItems_ReturnListWithLoanedBook(){
            Book testBook3 = new Book("testbook3", "a book for testing", "103", new Author("test", "McTest"));
            bookRepository.createBook(testBook3);
            LendItemDto testLendItem = RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                    .with().queryParam("isbn", "103")
                    .when().post("lending/book")
                    .then().statusCode(201).extract().body().as(LendItemDto.class);
            List<LendItemDto> result = RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json")
                    .when().get("lending/1")
                    .then().statusCode(200).extract().body().as(new TypeRef<List<LendItemDto>>() {
                    });
            System.out.println(result);
            Assertions.assertEquals(result.get(2), testLendItem);
        }
        @Test
        void whenMemberLoansNoBook_LibrarianAsksLendItems_ReturnEmptyList(){

            List<LendItemDto> result = RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json")
                    .when().get("lending/4")
                    .then().statusCode(200).extract().body().as(new TypeRef<List<LendItemDto>>() {
                    });
            Assertions.assertTrue(result.isEmpty());
        }
    }
}
