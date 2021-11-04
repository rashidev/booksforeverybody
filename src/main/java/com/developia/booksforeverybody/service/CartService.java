package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.CartEntity;

public interface CartService {
    CartEntity getCart(String username);

    void addBookToCart(String username, Long bookId);

    void deleteBookFromCart(String username, Long bookId);

}
