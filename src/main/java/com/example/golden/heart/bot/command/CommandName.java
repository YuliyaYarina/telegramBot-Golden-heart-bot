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
        ADDRESS("/address"),
        SECURITY("/security"),
        SAFETYPRECAUTIONS("/safetyPrecautions"),

    TAKEANANIMAL("/takeAnAnimal"),
        RULES("/rules"),
        DOCUMENTATION("/documentation"),
        RECOMMENDATION("/recommendation"),
            TRANSPORTATION("/transportation"),
            HOMEIMPROVEMENTYOUNG("/homeImprovementYoung"),
            HOMEIMPROVEMENTABULT("/homeImprovementAdult"),
            HOMEIMPROVEMENTFORDISABLED("/homeImprovementForDisabled"),
            PROVENDOGHANDLERS("/provenDogHandlers"),
        DOGHANDLERADVICE("/dogHandlerAdvice"),
        REASONEFORREFUSAL("/reasonsForRefusal"),

    REPORT("/report"),

    INFO("/info"),
    CONTACTDETAILS("/contactDetails"),
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
