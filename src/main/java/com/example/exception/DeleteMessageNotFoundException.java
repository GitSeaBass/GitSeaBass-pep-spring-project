package com.example.exception;

public class DeleteMessageNotFoundException extends RuntimeException {
    public DeleteMessageNotFoundException(String message) {
        super(message);
    }
}
