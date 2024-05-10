package com.example.golden.heart.bot.exceptions;

import java.io.IOException;

public class VolunteerAlreadyAppointedException extends IOException {

    public VolunteerAlreadyAppointedException() {
        super("Ответственный волонтер уже назначен");
    }
}
