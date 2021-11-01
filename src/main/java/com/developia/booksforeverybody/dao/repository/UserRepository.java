package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findUserByUsername(String username);
}
