package com.switchfully.digibooky.domain;

import java.util.UUID;

public abstract class LibraryItem {
    private final String id;
    private String title;
    private String description;

    public LibraryItem(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
    }

    public LibraryItem(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) {
        this.description = description;
    }
}
