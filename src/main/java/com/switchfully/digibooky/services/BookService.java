package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.BookDto;
import com.switchfully.digibooky.api.dtos.CreateBookDto;
import com.switchfully.digibooky.api.dtos.UpdateBookDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.LendInfoBook;
import com.switchfully.digibooky.domain.LendItem;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.domain.repositories.LendingRepository;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.services.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final UserRepository userRepository;
    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    public BookService(UserRepository userRepository, LendingRepository lendingRepository, BookRepository bookRepository, BookMapper bookMapper) {
        this.userRepository = userRepository;
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        return bookMapper.toDto(bookRepository.getAllBooks());
    }

    public List<BookDto> getAllBooksWithLoanStatusAndLoaner() {
        return bookRepository.getAllBooks().stream().map(book -> bookMapper.toDto(book, getLoanerName(book))).toList();
    }

    private LendInfoBook getLoanerName(Book book) {
        if (!book.getIsLended()) {
            return new LendInfoBook(String.valueOf(book.getIsLended()), "", "");
        }
        return userRepository.getAllUsers().stream().
                filter(user -> user.getId().equals(lendingRepository.getLendingMap().values().stream()
                        .filter(lendItem -> lendItem.getItemId().equals(book.getId()))
                        .map((LendItem::getMemberId)).findFirst().orElseThrow()))
                .findFirst()
                .map(user -> new LendInfoBook(String.valueOf(book.getIsLended()), user.getSurname(), user.getName()))
                .orElseThrow();
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

    public List<BookDto> searchBookByAuthor(String firstname, String lastname) throws NoSuchElementException {
        String regexFirstname = convertStringToRegularExpression(firstname).toLowerCase();
        String regexLastname = convertStringToRegularExpression(lastname).toLowerCase();

        List<BookDto> booksForGivenAuthor = bookMapper.toDto(bookRepository.getAllBooks().stream()
                .filter(book -> ((book.getAuthor()).getFirstname().toLowerCase().matches(regexFirstname))
                        && (book.getAuthor()).getLastname().toLowerCase().matches(regexLastname))
                .toList());
        if (booksForGivenAuthor.isEmpty()) {
            throw new NoSuchElementException("No book(s) matches for given (partial) authors first- or lastname.");
        }
        return booksForGivenAuthor;
    }

    public BookDto createBook(CreateBookDto createBookDto) {
        String error = validateInput(createBookDto);
        if (!error.isEmpty()) throw new IllegalArgumentException("Following fields are invalid: " + error);
        Book book = new Book(createBookDto.title(), createBookDto.description(), createBookDto.isbn(), createBookDto.author());
        return bookMapper.toDto(bookRepository.createBook(book));
    }

    public BookDto updateBook(UpdateBookDto updateBookDto, String id) {

        Book book = bookRepository.getBookByIdLibrarian(id).orElseThrow(() -> new IllegalArgumentException("No book with id: " + id));
        if (!updateBookDto.title().isEmpty()) {
            book.setTitle(updateBookDto.title());
        }
        if (!updateBookDto.description().isEmpty()) {
            book.setDescription(updateBookDto.description());
        }
        if (updateBookDto.author() != null) {
            if (!updateBookDto.author().getFirstname().isEmpty()) {
                book.setAuthorFirstName(updateBookDto.author().getFirstname());
            }
            if (!updateBookDto.author().getLastname().isEmpty()) {
                book.setAuthorLastName(updateBookDto.author().getLastname());
            }
        }
        book.setDeleted(updateBookDto.isDeleted());
        return bookMapper.toDto(book);
    }

    private String convertStringToRegularExpression(String string) {
        return string
                .replace("*", ".*")
                .replace("?", ".?");
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

    public BookDto deleteBook(String id) {
        bookRepository.deleteBook(id).orElseThrow(() -> new IllegalArgumentException("Book is already deleted or doesn't exist")).setDeleted(true);
        return bookMapper.toDto(bookRepository.getBookByIdLibrarian(id).orElseThrow(() -> new IllegalArgumentException("Book doesn't exist")));
    }
}
