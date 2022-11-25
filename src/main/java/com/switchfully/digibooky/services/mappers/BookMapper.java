package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {


    public BookDto toDto(Book book) {
        return new BookDto(book.getTitle(), book.getDescription(), book.getIsbn(), book.getAuthor(), book.getIsDeleted(), book.getIsLended());
    }

    public List<BookDto> toDto(List<Book> allBooks) {
        return allBooks.stream()
                .map(this::toDto)
                .toList();
    }
}
