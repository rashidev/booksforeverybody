package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String getSignInPage() {
        return "sign-in";
    }

    @PostMapping("/sign-up")
    public String createUser(@Valid @ModelAttribute("user") UserEntity user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        userService.register(user);
        return "redirect:/books";
    }
}
