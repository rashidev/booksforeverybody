package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.CartEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {

    Optional<CartEntity> findByUserId(Long userId);
}
