package com.developia.booksforeverybody.controller;

import com.developia.booksforeverybody.exception.NotFoundException;
import com.developia.booksforeverybody.exception.UserAlreadyExistsException;
import com.developia.booksforeverybody.exception.UsernameOrPasswordIncorrectException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(UsernameOrPasswordIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handle(UsernameOrPasswordIncorrectException ex) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("error-page");
        mv.addObject("details", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handle(UserAlreadyExistsException ex) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("error-page");
        mv.addObject("details", ex.getMessage());
        return mv;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ModelAndView handle(BindException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error-page");
        List<String> results = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            results.add(error.getField() + ":" + error.getDefaultMessage());
        }
        mav.addObject("details", results.toString());
        return mav;
    }
}
