package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;

public record CreateBookDto (String title, String description, String isbn, String firstname, Author author){}
