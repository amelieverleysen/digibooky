package com.switchfully.digibooky.domain.repositories;


import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book("1", "The Lord Of The Rings: The Return Of The King", "Something with wizards and hobits.", "9780395647400", new Author("JJR", "Tolkien")));
        books.add(new Book("2", "Mathilda", "Something with a little girl and telepathy", "9780435123987", new Author("Roald", "Dahl")));
        books.add(new Book("3", "1984", "Big Brother is watching.", "9780151660346", new Author("George", "Orwell")));
        books.add(new Book("4", "The Lord Of The Rings: The Two Towers", "Something with even more wizards and hobits and some orks.", "9780395647400", new Author("JJR", "Tolkien")));
    }

    public BookRepository(List<Book> booksToAdd) {
        books.addAll(booksToAdd);
    }

    public List<Book> getAllBooks() {
        List<Book> notDeletedBooks = new ArrayList<>();

        for (Book book : books) {
            if (!book.getIsDeleted()) {

                notDeletedBooks.add(book);
            }
        }
        return notDeletedBooks;
    }

    public Optional<Book> getBookById(String id) {

        return getAllBooks().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Optional<Book> getBookByIdLibrarian(String id) {

        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book createBook(Book bookToCreate) {
        books.add(bookToCreate);
        return bookToCreate;
    }

    public Optional<Book> deleteBook(String id) {
        return getBookByIdLibrarian(id);
    }
}

