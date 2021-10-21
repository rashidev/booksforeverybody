package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
}
