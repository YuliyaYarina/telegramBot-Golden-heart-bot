package com.example.golden.heart.bot.command;

import javax.xml.stream.events.Comment;

/**
 * enum для хранение имена всех команд
 * Имя команды должен быть уникальным
 */
public enum CommandName {

    START("/start"),
    INFO("/info"),
    VOLUNTEER("/volonter"),
    CAT("/cat"),
    DOG("/dog"),
    BACK("/back");

    private final String command;

    CommandName(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
