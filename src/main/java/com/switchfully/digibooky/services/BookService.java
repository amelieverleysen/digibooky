package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.services.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public List<BookDto> getAllBooks(){return bookMapper.toDto(bookRepository.getAllBooks());}

    public BookDto getBookById(String id) throws NoSuchElementException{
        return bookMapper.toDto(bookRepository
                .getBookById(id)
                .orElseThrow(() -> new NoSuchElementException("No book with id: " + id + " in our book database.")));
    }
}
