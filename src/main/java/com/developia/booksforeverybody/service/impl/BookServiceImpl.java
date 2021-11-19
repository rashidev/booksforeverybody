package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import com.developia.booksforeverybody.dao.entity.CommentEntity;
import com.developia.booksforeverybody.dao.entity.CommentStatus;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.BookRepository;
import com.developia.booksforeverybody.dao.repository.CommentRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAllByStatusIsNot(BookStatus.DELETED);
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRepository.findByIdAndStatusIsNot(id, BookStatus.DELETED).orElseThrow(
                () -> {
                    throw new NotFoundException("Book not found");
                }
        );
    }

    @Override
    public void addReview(String username, CommentEntity comment, Long bookId) {
        UserEntity user = userRepository.findUserByUsername(username).orElseThrow(
                () -> {
                    throw new NotFoundException("User not found!");
                }
        );
        comment.setUser(user);
        comment.setStatus(CommentStatus.CREATED);
        comment.setBookId(bookId);
        commentRepository.save(comment);

    }

    @Override
    public void createBook(BookEntity bookEntity) {
        bookEntity.setStatus(BookStatus.CREATED);
        bookEntity.setCreatedAt(LocalDateTime.now());
        bookEntity.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(bookEntity);
    }

    @Override
    public void deleteBook(Long bookId) {

        BookEntity book = bookRepository.findById(
                bookId).orElseThrow(
                () -> {
                    throw new NotFoundException("Book not found");
                }
        );
        book.setStatus(BookStatus.DELETED);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(BookEntity newBookData, Long bookId) {

        BookEntity book = bookRepository.findByIdAndStatusIsNot(
                bookId, BookStatus.DELETED).orElseThrow(
                () -> {
                    throw new NotFoundException("Book not found");
                }
        );
        book.setName(newBookData.getName());
        book.setCategory(newBookData.getCategory());
        book.setDescription(newBookData.getDescription());
        book.setAuthor(newBookData.getAuthor());
        book.setPrice(newBookData.getPrice());
        book.setPublisher(newBookData.getPublisher());
        book.setUpdatedAt(LocalDateTime.now());
        book.setYear(newBookData.getYear());
        book.setImage(newBookData.getImage());
        bookRepository.save(book);

    }

    @Override
    public void deleteReview(Long reviewId) {
        CommentEntity commentEntity = commentRepository.findById(reviewId).orElseThrow(
                () -> {
                    throw new NotFoundException("comment not found");
                }
        );
        commentEntity.setStatus(CommentStatus.DELETED);
        commentRepository.save(commentEntity);
    }

}
