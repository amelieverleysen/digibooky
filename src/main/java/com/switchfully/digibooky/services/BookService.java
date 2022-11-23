package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.services.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public List<BookDto> getAllBooks(){return bookMapper.toDto(bookRepository.getAllBooks());}
}
