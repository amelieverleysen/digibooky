package com.switchfully.digibooky.services.mappers;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.LendInfoBook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(book.getTitle(), book.getDescription(), book.getIsbn(), book.getAuthor(), null);
    }

    public List<BookDto> toDto(List<Book> allBooks) {
        return allBooks.stream()
                .map(this::toDto)
                .toList();
    }

    public BookDto toDto(Book book, LendInfoBook lendInfoBook) {
        return new BookDto(book.getTitle(), book.getDescription(), book.getIsbn(), book.getAuthor(), lendInfoBook);
    }
}
