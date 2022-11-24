package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService service) {
        this.bookService = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooks() {
        logger.info("method getAllBooks() is called");
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @GetMapping(params = "isbn", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> SearchBooksByISBN(@RequestParam String isbn){
        logger.info("method SearchBooksByISBN() is called");
        return bookService.SearchBooksByISBN(isbn);
    }
}
