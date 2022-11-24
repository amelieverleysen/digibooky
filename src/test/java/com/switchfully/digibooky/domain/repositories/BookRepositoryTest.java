package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;


class BookRepositoryTest {
    @Nested
    @DisplayName("Book repository Tests")
    public class RepositoryTests {
        @Test
        void givenBookRepository_WhenGetAllBooks_ReturnListOfAllBooks() {
            List<Book> testBooks = List.of(
                    new Book("1", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", new Author("JJR", "Tolkien")),
                    new Book("2", "Mathilda", "Something with a little girl and telepathy", "9780435123987", new Author("Roald", "Dahl")),
                    new Book("3", "1984", "Big Brother is watching.", "97801516603469", new Author("George","Orwell")));

            BookRepository testRepo = new BookRepository(testBooks);

            Assertions.assertEquals(testRepo.getAllBooks(), testBooks);
        }

        @Test
        void givenABookRepository_WhenGettingABookByIdIfTheBookExists_returnBookDetails() {
            List<Book> testBooks = List.of(new Book("1", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", new Author("JJR", "Tolkien")));
            BookRepository testRepo = new BookRepository(testBooks);

            Book testBook = testRepo.getBookById("1").orElse(null);

            Assertions.assertEquals(testBooks.get(0), testBook);
        }

        @Test
        void givenABookRepository_WhenGettingABookByIdIfTheBookDoesNotExists_ThenReturnNull() {
            List<Book> testBooks = List.of(new Book("1", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", new Author("JJR", "Tolkien")));
            BookRepository testRepo = new BookRepository(testBooks);

            Book testBook = testRepo.getBookById("2").orElse(null);

            Assertions.assertNull(testBook);

        }

        @Test
        void givenABookRepository_whenCreateBook_thenBookIsAddedToRepository() {
            BookRepository testRepo = new BookRepository();
            Book book = new Book("7", "Testbook","Something to test the books", "123456789", new Author("JJR", "Tolkien"));

            testRepo.createBook(book);
            Assertions.assertEquals(book, testRepo.getBookById("7").orElse(null));
        }
    }
}