package com.example.golden.heart.bot.command;

import javax.xml.stream.events.Comment;

/**
 * enum для хранение имена всех команд
 * Имя команды должен быть уникальным
 */
public enum CommandName {

    START("/start"),

    CAT("/cat"),
    DOG("/dog"),

    STARTINFO("/startInfo"),
    TAKEANANIMAL("/takeAnAnimal"),
    REPORT("/report"),

    INFO("/info"),
    VOLUNTEER("/volonter"),
    BACK("/back");

    private final String command;

    CommandName(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
