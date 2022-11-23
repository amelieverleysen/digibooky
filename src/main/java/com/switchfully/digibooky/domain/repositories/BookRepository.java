package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book("1234","The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", "JRR Tolkien"));
        books.add(new Book("1235","Mathilda", "Something with a little girl and telepathy", "9780435123987", "Roald Dahl"));
        books.add(new Book("1236","1984","Big Brother is watching.","9780151660346","George Orwell"));

    }

    public List<Book> getAllBooks() {
        return books;
    }
}
