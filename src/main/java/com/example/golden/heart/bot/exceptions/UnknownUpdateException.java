package com.example.golden.heart.bot.exceptions;

public class UnknownUpdateException extends RuntimeException {
    public UnknownUpdateException() {
    }

    public UnknownUpdateException(String message) {
        super(message);
    }
}
