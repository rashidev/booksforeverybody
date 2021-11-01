package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.BookRepository;
import com.developia.booksforeverybody.dao.repository.CartRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;



    public CartServiceImpl(UserRepository userRepository,
                           CartRepository cartRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public CartEntity getCart(String username) {
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");

                        });

        CartEntity cart =  cartRepository
               .findByUserId(user.getId()).orElseThrow(
                ()->{
                    throw new NotFoundException("Cart not found!");
                }
        );
       return cart;
    }

    @Override
    public void addBookToCart(String username, Long bookId) {
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");
                        }
                );

        BookEntity book =   bookRepository
                .findByIdAndStatusIsNot(bookId, BookStatus.DELETED)
                .orElseThrow(
                        () -> {
                            throw  new NotFoundException("Book not found!");
                        }
                );
        CartEntity cart = cartRepository
                .findByUserId(user.getId()).orElseThrow(
                        () -> {
                            throw  new NotFoundException("Cart not found!");

                        }
                );
        List<BookEntity> books = cart.getBooks();
        books.add(book);
        cart.setBooks(books);
        cartRepository.save(cart);
    }

}
