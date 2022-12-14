package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LentBookMemberTest {
    @LocalServerPort
    int port;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Tests regarding LendItem lists for members")
    @Nested
    class lentBooksMemberTest {
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
            Assertions.assertTrue(result.contains(testLendItem));
        }
        @Test
        void whenMemberLoansNoBook_LibrarianAsksLendItems_ReturnEmptyList(){
            RestAssured.given().port(port).auth().preemptive().basic("2", "pwd").log().all().contentType("application/json")
                    .when().get("lending/")
                    .then().assertThat().statusCode(404);
        }
    }
}
