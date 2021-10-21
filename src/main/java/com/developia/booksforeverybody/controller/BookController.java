package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    @GetMapping
    public String getBooks(Model model) {
        var books = service.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        var book = service.getBookById(id);
        model.addAttribute("book", book);
        return "book";
    }
}
