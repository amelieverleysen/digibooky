package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateBookDto;
import com.switchfully.digibooky.domain.security.Feature;
import com.switchfully.digibooky.services.BookService;
import com.switchfully.digibooky.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final SecurityService securityService;


    public BookController(BookService service, SecurityService securityService) {
        this.bookService = service;
        this.securityService = securityService;
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
    public List<BookDto> searchBooksByISBN(@RequestParam String isbn){
        logger.info("method SearchBooksByISBN() is called");
        return bookService.searchBooksByISBN(isbn);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createBookDto, @RequestHeader String authorization) {
       securityService.validateAuthorisation(authorization, Feature.CREATE_BOOK);
        return bookService.createBook(createBookDto);
    }
}
