package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;

public record UpdateBookDto(String title, String description, Author author, Boolean isDeleted) {
}
