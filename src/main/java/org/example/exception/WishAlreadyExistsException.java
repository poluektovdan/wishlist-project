package org.example.exception;

public class WishAlreadyExistsException extends RuntimeException {
    public WishAlreadyExistsException(String message) {
        super(message);
    }
}
