package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class duplicateBookLoanTest {
    @LocalServerPort
    int port;


    @Autowired
    private BookRepository bookRepository;


    @DisplayName("test with duplicate books/loans")
    @Nested
    public class MultipleBookTests {
        @Test
        void givenTwoTestBooks_BothAvailable_Return1() {
            Book testBook = new Book("testbook", "a book for testing", "100", new Author("test", "McTest"));
            Book testBook2 = new Book("testbook", "a book for testing", "100", new Author("test", "McTest"));
            bookRepository.createBook(testBook);
            bookRepository.createBook(testBook2);

            LendItemDto result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("isbn", "100")
                            .when().post("lending/book")
                            .then().statusCode(201).and().extract().body().as(LendItemDto.class);
            System.out.println(result);

            assertEquals(testBook.getId(), result.itemId());
        }

        @Test
        void givenTwoTestBooks_OneAlreadyLoanedOut_LendOtherBook() {
            Book testBookUnique = new Book("testbookU", "a book for testing", "101", new Author("test", "McTest"));
            bookRepository.createBook(testBookUnique);
            Book testBookUnique2 = new Book("testbookU", "a book for testing", "101", new Author("test", "McTest"));
            bookRepository.createBook(testBookUnique2);
            testBookUnique.setIsLended(true);

            LendItemDto result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("isbn", "101")
                            .when().post("lending/book")
                            .then().statusCode(201).and().extract().body().as(LendItemDto.class);

            assertEquals(testBookUnique2.getId(), result.itemId());

        }
        @Test
        void givenLoanedOutBook_WhenTryingToLoan_ThrowsNoSuchElementExc() {
            Book testBookIsLended = new Book("testbooklend", "a book for testing", "1005", new Author("test", "McTest"));
            testBookIsLended.setIsLended(true);
            Map<String, String> result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("isbn", "100")
                            .when().post("lending/book")
                            .then().statusCode(404).and().extract().
                            body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(result).get("message").toString();
            assertEquals("Book with isbn: 100 not found", ResponseMessage);
        }
    }
    @AfterEach
    void cleanup(){
        RestAssured.reset();
    }
}
