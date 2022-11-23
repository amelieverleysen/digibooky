package com.switchfully.digibooky.domain;

import java.util.Objects;

public class Book extends LibraryItem {
    private final String isbn;
    private final String author;

    public Book(String title, String description, String isbn, String author) {
        super(title, description);
        this.isbn = isbn;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isbn, author);
    }
}
