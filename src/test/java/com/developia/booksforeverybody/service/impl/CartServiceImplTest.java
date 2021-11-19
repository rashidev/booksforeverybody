package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.BookRepository;
import com.developia.booksforeverybody.dao.repository.CartRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CartServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        System.out.println("Before each");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBookToCart_errorUserNotFound() {

        Mockito.when(userRepository.findUserByUsername("user"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                NotFoundException.class,
                () -> cartService.addBookToCart("user", 1L)
        );
    }

    @Test
    void addBookToCart_errorBookNotFound() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Mockito.when(userRepository.findUserByUsername("user"))
                .thenReturn(Optional.of(user));

        Mockito.when(bookRepository.findByIdAndStatusIsNot(1L, BookStatus.DELETED))
                .thenReturn(Optional.empty());

        var ex = Assertions.assertThrows(
                NotFoundException.class,
                () -> cartService.addBookToCart("user", 1L)
        );

        assert ex.getMessage().equals("Book not found!");
    }

    @Test
    void addBookToCart_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        BookEntity book = new BookEntity();
        CartEntity cart = new CartEntity();

        Mockito.when(userRepository.findUserByUsername("user"))
                .thenReturn(Optional.of(user));

        Mockito.when(bookRepository.findByIdAndStatusIsNot(1L, BookStatus.DELETED))
                .thenReturn(Optional.of(book));

        Mockito.when(cartRepository.findByUserId(1L))
                .thenReturn(Optional.of(cart));

        cartService.addBookToCart("user", 1L);

        assert cart.getBooks().size() == 1;
        assert cart.getUserId() == 1L;
    }
}