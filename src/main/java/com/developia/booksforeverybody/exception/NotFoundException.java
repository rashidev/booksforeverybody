package com.developia.booksforeverybody.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
