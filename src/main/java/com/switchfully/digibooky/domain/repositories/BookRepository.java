package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book("The Lord Of The Rings", "Something with wizards and hobits.", "34061689", "JRR Tolkien"));
        books.add(new Book("Mathilda", "Something with a little girl and telepathy", "3454153168", "Roald Dahl"));
        books.add(new Book("1984","Big Brother is watching.","468435468","George Orwell"));

    }

    public List<Book> getAllBooks() {
        return books;
    }
}
