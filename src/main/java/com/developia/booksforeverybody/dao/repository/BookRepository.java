package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository <BookEntity, Long>{
   List<BookEntity> findAllByStatusIsNot(BookStatus status);

   Optional<BookEntity >findByIdAndStatusIsNot(Long id, BookStatus status);
}
