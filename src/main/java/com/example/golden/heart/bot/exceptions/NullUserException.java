package com.example.golden.heart.bot.exceptions;

public class NullUserException extends RuntimeException {
    public NullUserException() {
    }

    public NullUserException(String message) {
        super(message);
    }
}
