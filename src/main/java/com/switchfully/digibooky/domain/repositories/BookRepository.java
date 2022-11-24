package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book("1", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", "JRR Tolkien"));
        books.add(new Book("2", "Mathilda", "Something with a little girl and telepathy", "9780435123987", "Roald Dahl"));
        books.add(new Book("3", "1984", "Big Brother is watching.", "9780151660346", "George Orwell"));
    }

    public BookRepository(List<Book> booksToAdd){
        books.addAll(booksToAdd);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
