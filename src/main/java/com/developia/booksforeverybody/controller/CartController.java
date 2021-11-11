package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCart(Model model, Authentication authentication) {
        var cart = cartService.getCart(authentication.getName());

        model.addAttribute("cart", cart);

        return "cart";
    }

    @PostMapping("/add/book/{bookId}")
    public String addBook(@PathVariable("bookId") Long bookId,
                          Authentication authentication) {
        cartService.addBookToCart(authentication.getName(), bookId);

        return "redirect:/carts";
    }

    @PostMapping("/remove/book/{bookId}")
    public String removeBook(@PathVariable("bookId") Long bookId,
                             Authentication authentication) {
        cartService.deleteBookFromCart(authentication.getName(), bookId);

        return "redirect:/carts";
    }
}
