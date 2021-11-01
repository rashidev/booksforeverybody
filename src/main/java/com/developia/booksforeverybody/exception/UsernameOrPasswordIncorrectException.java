package com.developia.booksforeverybody.exception;

public class UsernameOrPasswordIncorrectException extends RuntimeException {
    public UsernameOrPasswordIncorrectException(String message) {
        super(message);
    }
}
