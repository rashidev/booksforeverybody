package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.CartRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
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

}
