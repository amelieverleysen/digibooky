package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    @Test
    void givenBookRepository_WhenGetAllBooks_ReturnListOfAllBooks() {
        List<Book> testBooks = new ArrayList<Book>();
        testBooks.add(new Book("1234", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", "JRR Tolkien"));
        testBooks.add(new Book("1235", "Mathilda", "Something with a little girl and telepathy", "9780435123987", "Roald Dahl"));
        testBooks.add(new Book("1236", "1984", "Big Brother is watching.", "9780151660346", "George Orwell"));

        BookRepository testRepo = new BookRepository();

        Assertions.assertEquals(testRepo.getAllBooks(), testBooks);
    }
}