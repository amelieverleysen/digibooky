package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateBookDto;
import com.switchfully.digibooky.domain.Book;
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

    public List<BookDto> getAllBooks() {
        return bookMapper.toDto(bookRepository.getAllBooks());
    }

    public BookDto getBookById(String id) throws NoSuchElementException {
        return bookMapper.toDto(bookRepository
                .getBookById(id)
                .orElseThrow(() -> new NoSuchElementException("No book with id: " + id + " in our book database.")));
    }

    public List<BookDto> searchBooksByISBN(String isbn) throws NoSuchElementException {
        String regexIsbnWithoutHyphen = convertStringToRegularExpression(isbn
                .replace("-", ""));

        List<BookDto> booksForGivenIsbn = bookMapper.toDto(bookRepository.getAllBooks().stream()
                .filter(book -> (book.getIsbn()).matches(regexIsbnWithoutHyphen))
                .toList());

        if (booksForGivenIsbn.isEmpty()) {
            throw new NoSuchElementException("No book(s) matches for given (partial) isbn.");
        }
        return booksForGivenIsbn;
    }

    public List<BookDto> searchBooksByTitle(String title) {
        String regexTitle = convertStringToRegularExpression(title).toLowerCase();

        List<BookDto> booksForGivenTitle = bookMapper.toDto(bookRepository.getAllBooks().stream()
                .filter(book -> (book.getTitle()).toLowerCase().matches(regexTitle))
                .toList());

        if (booksForGivenTitle.isEmpty()) {
            throw new NoSuchElementException("No book(s) matches for given (partial) title.");
        }
        return booksForGivenTitle;
    }

    private String convertStringToRegularExpression(String string) {
        return string
                .replace("*", ".*")
                .replace("?", ".?");
    }

    public BookDto createBook(CreateBookDto createBookDto) {
        String error = validateInput(createBookDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);
        Book book = new Book(createBookDto.title(), createBookDto.description(), createBookDto.isbn(), createBookDto.author());
        return bookMapper.toDto(bookRepository.createBook(book));
    }

    public String validateInput(CreateBookDto createBookDto) {
        String result = "";
        if (createBookDto.title().isEmpty()) {
            result += " title ";
        }
        if (createBookDto.description().isEmpty()) {
            result += " description ";
        }
        if (createBookDto.isbn().isEmpty()) {
            result += " isbn ";
        }
        if (createBookDto.author().getLastname().isEmpty()) {
            result += " author lastname ";
        }
        return result;
    }
}
