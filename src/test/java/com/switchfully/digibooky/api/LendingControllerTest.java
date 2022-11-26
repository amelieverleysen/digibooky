package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.api.dtos.ReturnLibraryItemDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.LendItem;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.domain.repositories.LendingRepository;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.NestedTestConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class LendingControllerTest {
    @LocalServerPort
    int port;


   @Autowired
    private BookRepository bookRepository;

   @Autowired
   private LendingRepository lendingRepository;

   private Book testBook;
   private String testMemberId;
   private String testMemberPw;
   private Book bookTolend;

   @BeforeEach
   void initializeData(){
       testBook = new Book("testbook", "a book for testing", "100", new Author("test", "McTest"));
       bookTolend = new Book("lend title", "a book for lending", "900", new Author("test", "McTest"));
       bookRepository.createBook(testBook);
       testMemberId = "1";
       testMemberPw = "pwd";
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



    @DisplayName("return test")
    @Nested
    class ReturnTest{
        @Test
        void givenAnExistingLend_whenReturningABook_thenResponseIdEquals() {
            bookRepository.createBook(bookTolend);
            lendingRepository.save(new LendItem("lendId", bookTolend.getId(), testMemberId));
            bookTolend.setIsLended(true);

            ReturnLibraryItemDto result =
                    RestAssured.given().port(port).auth().preemptive().basic(testMemberId, testMemberPw).log().all().contentType("application/json")
                            .with().queryParam("returnId", "lendId")
                            .when().post("lending/book")
                            .then().statusCode(200).and().extract().body().as(ReturnLibraryItemDto.class);

            assertEquals("lendId", result.id());
        }

        @Test
        void givenAnExistingLend_whenReturningABookToLate_thenResponseIdEquals() {
            bookRepository.createBook(bookTolend);
            lendingRepository.save(new LendItem("lendId", bookTolend.getId(), testMemberId, LocalDate.of(2022, 06, 20)));
            bookTolend.setIsLended(true);

            Map<String, String> result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("returnId", "lendId")
                            .when().post("lending/book")
                            .then().statusCode(200).and().extract().
                            body().as(new TypeRef<Map<String, String>>() {
                            });

            String isInTime = new JSONObject(result).get("inTime").toString();
            assertEquals("false", isInTime);
        }

        @Test
        void givenAnUnknownLoan_whenReturningAnUnknownId_thenResponseMessage() {
            Map<String, String> result =
                    RestAssured.given().port(port).auth().preemptive().basic("1", "pwd").log().all().contentType("application/json")
                            .with().queryParam("returnId", "x")
                            .when().post("lending/book")
                            .then().statusCode(404).and().extract().
                            body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(result).get("message").toString();
            assertEquals("No lend item found for Id: x", ResponseMessage);
        }
        @Test
        void givenAnExistingLend_whenAWrongUserReturns_thenResponseMessage() {
            bookRepository.createBook(bookTolend);
            lendingRepository.save(new LendItem("lendId", bookTolend.getId(), testMemberId));
            bookTolend.setIsLended(true);

            Map<String, String> result =
                    RestAssured.given().port(port).auth().preemptive().basic("4", "pwd").log().all().contentType("application/json")
                            .with().queryParam("returnId", "lendId")
                            .when().post("lending/book")
                            .then().statusCode(400).and().extract()
                            .body().as(new TypeRef<Map<String, String>>() {
                            });

            String ResponseMessage = new JSONObject(result).get("message").toString();
            assertEquals("Unauthorized", ResponseMessage);
        }
    }
    @AfterEach
    void cleanup(){
        RestAssured.reset();
    }
}