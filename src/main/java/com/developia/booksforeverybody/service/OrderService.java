package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void createOrder(String username);

    List<OrderEntity> getOrders(String username);

    BigDecimal getTotalPrice(String username);
}
