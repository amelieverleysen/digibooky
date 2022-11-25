package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LendingControllerTest {
    @LocalServerPort
    int port;


   @Autowired
    private BookRepository bookRepository;

   private Book testBook;

   @BeforeEach
   void initializeData(){
       testBook = new Book("testbook", "a book for testing", "100", new Author("test", "McTest"));
       bookRepository.createBook(testBook);
   }

    @Test
    void givenAnAuthorizedUser_whenLendingABook_thenBookIdEquals() {
        LendItemDto result =
                RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                        .with().queryParam("isbn", "100")
                        .when().post("lending/book")
                        .then().statusCode(201).and().extract().body().as(LendItemDto.class);

        assertEquals(testBook.getId(), result.itemId());
    }

    @DisplayName("Authorization test")
    @Nested
    class AuthorizationCheck{
        @Test
        void givenAUnKnownUser_whenLendingABook_thenThrowsUnknownUserException() {
            Map<String, String> response =
                    RestAssured.given().port(port).auth().preemptive().basic("0", "pwd").log().all().contentType("application/json")
                            .with().queryParam("isbn", "100")
                            .when().post("lending/book")
                            .then().statusCode(403).and().extract().body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(response).get("message").toString();

            assertEquals("Unauthorized", ResponseMessage);
        }
        @Test
        void givenAWrongPassword_whenLendingABook_thenThrowsUnknownUserException() {
            Map<String, String> response =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "x").log().all().contentType("application/json")
                            .with().queryParam("isbn", "100")
                            .when().post("lending/book")
                            .then().statusCode(403).and().extract().body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(response).get("message").toString();

            assertEquals("Unauthorized", ResponseMessage);
        }
    }

    @DisplayName("test with duplicate books/loans")
    @Nested
    public class MultipleBookTests {
       @Test
        void givenTwoTestBooks_BothAvailable_Return1() {
           Book testBook2 = new Book("testbook", "a book for testing", "100", new Author("test", "McTest"));
           bookRepository.createBook(testBook2);

           LendItemDto result =
                   RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                           .with().queryParam("isbn", "100")
                           .when().post("lending/book")
                           .then().statusCode(201).and().extract().body().as(LendItemDto.class);

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
           testBook.setIsLended(true);
           Map<String, String> result =
                   RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                           .with().queryParam("isbn", "100")
                           .when().post("lending/book")
                           .then().statusCode(404).and().extract().
                            body().as(new TypeRef<Map<String, String>>() {
                   });

           String ResponseMessage = new JSONObject(result).get("message").toString();
           assertEquals("Book with isbn: 100 is not available", ResponseMessage);
       }
       @Test
        void givenDeletedBook_WhenTryingToLoan_ThrowsNoSuchElementExc() {
            testBook.setDeleted(true);
            Map<String, String> result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("isbn", "100")
                            .when().post("lending/book")
                            .then().statusCode(404).and().extract().
                            body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(result).get("message").toString();
            assertEquals("Book with isbn: 100 is not available", ResponseMessage);
        }
    }
}