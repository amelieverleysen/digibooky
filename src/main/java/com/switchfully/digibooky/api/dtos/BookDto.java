package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.LendInfoBook;

public record BookDto(String title, String description, String isbn, Author author, LendInfoBook lendInfoBook) {
}
