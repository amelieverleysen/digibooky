package com.switchfully.digibooky.domain;

public class Book extends LibraryItem {
    private final String isbn;
    private Author author;

    public Book(String title, String description, String isbn, Author author) {
        super(title, description);
        this.isbn = isbn;
        this.author = author;
    }

    public Book(String id, String title, String description, String isbn, Author author) {
        super(id, title, description);
        this.isbn = isbn;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthorFirstName(String firstName) {
        this.author.setFirstname(firstName);
    }

    public void setAuthorLastName(String lastName) {
        this.author.setLastname(lastName);
    }
}
