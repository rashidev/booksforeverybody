package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.dao.entity.BookEntity;
import com.developia.booksforeverybody.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/moderator")
@Controller
public class ModeratorController {
    private final BookService bookService;

    public ModeratorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/create-page")
    public String getBookCreationPage(Model model) {
        model.addAttribute("book", new BookEntity());
        return "book-create";
    }

    @PostMapping("/books/create")
    public String createBook(@ModelAttribute("book") BookEntity entity) {
        bookService.createBook(entity);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/update-page")
    public String getUpdateBookPage(Model model, @PathVariable("id") Long bookId) {
        BookEntity book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book-update";
    }

    @PostMapping("/books/{id}/update")
    public String updateBook(@ModelAttribute("book") BookEntity book,
                             @PathVariable("id") Long bookId) {
        bookService.updateBook(book, bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/{id}/review/{reviewId}/delete")
    public String deleteReview(@PathVariable("reviewId") Long reviewId,
                               @PathVariable("id") Long bookId) {
        bookService.deleteReview(reviewId);
        return "redirect:/books/" + bookId;
    }
}
