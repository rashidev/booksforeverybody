package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/confirm-page")
    public String getOrderPage(Authentication authentication, Model model) {
        var totalAmount = orderService.getTotalPrice(authentication.getName());
        model.addAttribute("totalAmount", totalAmount);
        return "order";
    }

    @GetMapping
    public String getOrders(Authentication authentication, Model model) {
        var orders = orderService.getOrders(authentication.getName());
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @PostMapping
    public String completeOrder(Authentication authentication) {
        orderService.createOrder(authentication.getName());
        return "redirect:/orders";
    }
}
