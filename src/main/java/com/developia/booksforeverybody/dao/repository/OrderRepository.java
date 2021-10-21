package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
