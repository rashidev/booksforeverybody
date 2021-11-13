package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.dao.entity.BookStatus;
import com.developia.booksforeverybody.dao.entity.CommentEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.BookRepository;
import com.developia.booksforeverybody.dao.repository.CommentRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private  final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;



    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAllByStatusIsNot(BookStatus.DELETED);
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRepository.findByIdAndStatusIsNot(id,BookStatus.DELETED).orElseThrow(
                ()->{
                  throw new NotFoundException("Book not found");
                }
        );
    }

    @Override
    public void addReview(String username, CommentEntity comment, Long bookId) {
        UserEntity user = userRepository.findUserByUsername(username).orElseThrow(
                ()->{
                    throw new NotFoundException("User not found!");
                }
        );
        comment.setUser(user);
        comment.setBookId(bookId);
        commentRepository.save(comment);

    }
}
