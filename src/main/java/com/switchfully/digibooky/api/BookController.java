package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateBookDto;
import com.switchfully.digibooky.api.dtos.UpdateBookDto;
import com.switchfully.digibooky.domain.security.Feature;
import com.switchfully.digibooky.services.BookService;
import com.switchfully.digibooky.services.SecurityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final SecurityService securityService;


    public BookController(BookService service, SecurityService securityService) {
        this.bookService = service;
        this.securityService = securityService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @GetMapping(params = "isbn", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> searchBooksByISBN(@RequestParam String isbn) {
        return bookService.searchBooksByISBN(isbn);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookDto createBookDto, @RequestHeader String authorization) {
       securityService.validateAuthorisation(authorization, Feature.CREATE_BOOK);
        return bookService.createBook(createBookDto);
    }

    @GetMapping(params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<BookDto> searchBookByTitle(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }
    @PutMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto updateBook(@RequestBody UpdateBookDto updateBookDto, @RequestHeader String authorization, @PathVariable String id){
        securityService.validateAuthorisation(authorization, Feature.UPDATE_BOOK);
        return bookService.updateBook(updateBookDto,id);
    }
}
