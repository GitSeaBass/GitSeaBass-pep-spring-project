package com.example.exception;

public class ConflictingUsernameException extends RuntimeException {
    public ConflictingUsernameException(String message) {
        super(message);
    }
}
