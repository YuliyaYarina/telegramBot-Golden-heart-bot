package com.example.golden.heart.bot.exception;

import java.io.IOException;

public class VolunteerAlreadyAppointedException extends IOException {
    public VolunteerAlreadyAppointedException() {
    }

    public VolunteerAlreadyAppointedException(String message) {
        super(message);
    }

    public VolunteerAlreadyAppointedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VolunteerAlreadyAppointedException(Throwable cause) {
        super(cause);
    }
}
