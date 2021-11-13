package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.CommentEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> getAllBooks();

    BookEntity getBookById(Long id);

    void addReview(String username, CommentEntity comment, Long bookId);
}
