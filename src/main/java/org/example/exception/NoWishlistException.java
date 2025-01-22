package org.example.exception;

public class NoWishlistException extends RuntimeException {
    public NoWishlistException(String message) {
        super(message);
    }
}
