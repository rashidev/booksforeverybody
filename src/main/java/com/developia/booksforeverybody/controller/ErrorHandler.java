package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle(HttpServletRequest request,
                               NotFoundException ex) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("error-page");
        mv.addObject("details", ex.getMessage());
        mv.addObject("path", request.getRequestURL());

        return mv;
    }
}
