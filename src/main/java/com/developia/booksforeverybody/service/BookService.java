package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.CommentEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> getAllBooks();

    BookEntity getBookById(Long id);

    void addReview(String username, CommentEntity comment,
                   Long bookId);

    void createBook(BookEntity bookEntity);

    void deleteBook(Long bookId);

    void updateBook(BookEntity bookEntity, Long bookId);

    void deleteReview(Long reviewId);

}

