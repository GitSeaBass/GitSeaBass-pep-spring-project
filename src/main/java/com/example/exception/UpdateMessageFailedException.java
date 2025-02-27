package com.example.exception;

public class UpdateMessageFailedException extends RuntimeException {
    public UpdateMessageFailedException(String message) {
        super(message);
    }
}
