package com.switchfully.digibooky.services;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.LendItem;
import com.switchfully.digibooky.domain.User;
import com.switchfully.digibooky.domain.repositories.BookRepository;
import com.switchfully.digibooky.domain.repositories.LendingRepository;
import com.switchfully.digibooky.domain.repositories.UserRepository;
import com.switchfully.digibooky.services.mappers.LendingMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.NoSuchElementException;

@Service
public class LendingService {
    private String userId;
    private LendingRepository lendingRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private LendingMapper lendingMapper;

    public LendingService(LendingRepository lendingRepository, BookRepository bookRepository, UserRepository userRepository, LendingMapper lendingMapper) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.lendingMapper = lendingMapper;
    }

    public LendItemDto lendBook(String isbn, String authorization) {
        userId = getUserId(authorization);
        String isbnNormalized = isbn.replace("-", "");
        Book book = bookRepository.getAllBooks().stream().filter(item -> item.getIsbn().equals(isbnNormalized)).findFirst().orElseThrow(() -> new NoSuchElementException("Book with isbn: " + isbn + "not found"));
        User user = userRepository.getUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return lendingMapper.toDTO(lendingRepository.save(new LendItem(book.getId(), user.getId())));
    }

    private String getUserId(String authorization){
        String decodedToUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        return decodedToUsernameAndPassword.split(":")[0];
    }
}
