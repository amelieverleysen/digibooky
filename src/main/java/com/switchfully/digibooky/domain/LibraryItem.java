package com.switchfully.digibooky.domain;

import java.util.UUID;

public abstract class LibraryItem {
    private final String id;
    private String title;
    private String description;
    private boolean isLended = false;
    private boolean isDeleted;

    public LibraryItem(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.isDeleted = false;
    }

    public LibraryItem(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isDeleted = false;
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

    public boolean getIsDeleted() { return isDeleted;  }

    public boolean getIsLended() {return isLended;}

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setTitle(String title) { this.title = title; }

    public void setIsLended(boolean newStatus) {this.isLended = newStatus;}

    public void setDescription(String description) {this.description = description;}
}
