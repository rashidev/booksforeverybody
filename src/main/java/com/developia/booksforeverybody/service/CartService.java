package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.model.BookDto;

import java.util.List;

public interface CartService {
    List<BookDto> getBooksFromCart(String username);

    void addBookToCart(String username, Long bookId);

    void deleteBookFromCart(String username, Long bookId);

}
