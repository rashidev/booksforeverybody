package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.BookEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> getAllBooks();

    BookEntity getBookById(Long id);
}
