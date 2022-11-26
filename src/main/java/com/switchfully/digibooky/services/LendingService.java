package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.api.dtos.ReturnLibraryItemDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.LendItem;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.domain.repositories.LendingRepository;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.services.mappers.LendingMapper;
import com.switchfully.digibooky.services.mappers.ReturnItemMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class LendingService {
    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LendingMapper lendingMapper;
    private final ReturnItemMapper returnItemMapper;
    private User user;
    private Book book;

    public LendingService(LendingRepository lendingRepository, BookRepository bookRepository, UserRepository userRepository, LendingMapper lendingMapper, ReturnItemMapper returnItemMapper) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.lendingMapper = lendingMapper;
        this.returnItemMapper = returnItemMapper;
    }

    public LendItemDto lendBook(String isbn, String authorization) throws NoSuchElementException{
        user = getUserByAuthorization(authorization);
        book = searchBookToLendByIsbn(isbn);
        book.setIsLended(true);
        return lendingMapper.toDTO(lendingRepository.save(new LendItem(book.getId(), user.getId())));
    }

    public ReturnLibraryItemDto returnBook(String returnId, String authorization) {
        LendItem lendItem = lendingRepository.getLendItemById(returnId).orElseThrow(() -> new NoSuchElementException("No lend item found for Id: " + returnId));
        user = getUserByAuthorization(authorization);
        if (!lendItem.getMemberId().equals(user.getId())) throw new IllegalArgumentException("Unauthorized");
        book = bookRepository.getBookById(lendItem.getItemId()).orElseThrow(() ->  new NoSuchElementException("Database inconsistency"));
        book.setIsLended(false);
        lendingRepository.removeLendItem(lendItem);

        return returnItemMapper.toDTO(lendItem);
    }

    private Book searchBookToLendByIsbn(String isbn) throws NoSuchElementException{
        String isbnNormalized = isbn.replace("-", "");
        List<Book> books = bookRepository.getAllBooks().stream().filter(item -> item.getIsbn().equals(isbnNormalized)).toList();
        if (books.isEmpty()) throw new NoSuchElementException("Book with isbn: " + isbn + " not found");

        for (Book book : books) {
            if (!book.getIsLended() && !book.getIsDeleted()){
                return book;
            }
        }
        throw  new NoSuchElementException("Book with isbn: " + isbn + " is not available");
    }


    private User getUserByAuthorization(String authorization){
        String decodedToUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String userId = decodedToUsernameAndPassword.split(":")[0];
        return userRepository.getUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<LendItemDto> getLendItemsForMember(String memberId) {
        return lendingMapper.toDTO(lendingRepository.getAllLendItems())
                .stream()
                .filter(lendItem -> memberId.equals(lendItem.memberId()))
                .collect(Collectors.toList());
    }

}
