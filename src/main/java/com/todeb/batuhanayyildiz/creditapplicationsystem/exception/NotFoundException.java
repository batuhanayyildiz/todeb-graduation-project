package com.todeb.batuhanayyildiz.creditapplicationsystem.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message + " not found!");
    }
}
