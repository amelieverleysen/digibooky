package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;

public record BookDto(String title, String description, String isbn, Author author, boolean isLended) {
}
