package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.BookRepository;
import com.developia.booksforeverybody.dao.repository.CartRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.model.BookDto;
import com.developia.booksforeverybody.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    //    Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
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
    public List<BookDto> getBooksFromCart(String username) {
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");

                        });

        CartEntity cart = cartRepository
                .findByUserId(user.getId()).orElseThrow(
                        () -> {
                            throw new NotFoundException("Cart not found!");
                        }
                );

        Map<Long, BookDto> bookMap = new HashMap<>();

        for (BookEntity book : cart.getBooks()) {
            BookDto bookDto;
            if (bookMap.containsKey(book.getId())) {
                bookDto = bookMap.get(book.getId());
                BigDecimal price = book.getPrice();
                BigDecimal totalPrice = bookDto.getTotalPrice();
                totalPrice = totalPrice.add(price); // for double totalPrice + price
                bookDto.setCount(bookDto.getCount() + 1);
                bookDto.setTotalPrice(totalPrice);
            } else {
                bookDto = BookDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .category(book.getCategory())
                        .price(book.getPrice())
                        .totalPrice(book.getPrice())
                        .count(1)
                        .build();
            }
            bookMap.put(book.getId(), bookDto);
        }
        List<BookDto> books = new ArrayList<>(bookMap.values());
        return books;
    }

    @Override
    public void addBookToCart(String username, Long bookId) {
        log.info("addBookToCart started");

        log.debug("findUserByUsername where username is {}", username);
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            log.error("findUserByUsername failed");
                            throw new NotFoundException("User not found!");
                        }
                );
        log.debug("findUserByUsername where username is {} ended", username);
        BookEntity book = bookRepository
                .findByIdAndStatusIsNot(bookId, BookStatus.DELETED)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Book not found!");
                        }
                );
        CartEntity cart = cartRepository
                .findByUserId(user.getId()).orElse(new CartEntity());
        List<BookEntity> books = cart.getBooks();
        books.add(book);
        cart.setUserId(user.getId());
        cart.setBooks(books);
        cartRepository.save(cart);

        log.info("addBookToCart ended");
    }

    @Override
    public void deleteBookFromCart(String username, Long bookId) {
        UserEntity user = userRepository
                .findUserByUsername(username)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");

                        }
                );
        CartEntity cart = cartRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Cart not found!");
                        }

                );
        BookEntity book = bookRepository
                .findByIdAndStatusIsNot(bookId, BookStatus.DELETED)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Book not found!");
                        }
                );
        List<BookEntity> books = cart.getBooks();
        books.remove(book);
        cart.setBooks(books);
        cartRepository.save(cart);
    }

}
