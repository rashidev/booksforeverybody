package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
