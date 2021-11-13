package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.OrderEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.CartRepository;
import com.developia.booksforeverybody.dao.repository.OrderRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserRepository userRepository,
                            CartRepository cartRepository,
                            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(String username) {
        CartEntity cart = getCart(username);
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<BookEntity> books = new ArrayList<>();
        for (BookEntity book : cart.getBooks()) {
            totalPrice = totalPrice.add(book.getPrice());
            books.add(book);
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTotalAmount(totalPrice);
        orderEntity.setBooks(books);
        orderEntity.setUserId(cart.getUserId());
        orderRepository.save(orderEntity);
        cart.setBooks(new ArrayList<>());
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(String username) {
        CartEntity cart = getCart(username);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BookEntity book : cart.getBooks()) {
            totalPrice = totalPrice.add(book.getPrice());
        }
        return totalPrice;
    }

    private CartEntity getCart(String username) {
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");
                        });

        return cartRepository
                .findByUserId(user.getId()).orElseThrow(
                        () -> {
                            throw new NotFoundException("Cart not found!");
                        }
                );
    }

    @Override
    public List<OrderEntity> getOrders(String username) {
        UserEntity user = userRepository
                .findUserByUsername(username).orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found!");

                        });

        List<OrderEntity> orders = orderRepository.findAllByUserId(user.getId());

        return orders;
    }
}
