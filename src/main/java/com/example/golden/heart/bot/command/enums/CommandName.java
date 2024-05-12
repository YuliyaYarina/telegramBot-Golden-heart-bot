package com.example.golden.heart.bot.command.enums;

/**
 * enum для хранение имена всех команд
 * Имя команды должен быть уникальным
 */
public enum CommandName {

    START("/start"),

       CAT("/cat"),
       DOG("/dog"),

    START_INFO("/startInfo"),
        ADDRESS("/address"),
        SECURITY("/security"),
        SAFETY_PRECAUTIONS("/safetyPrecautions"),

    TAKE_AN_ANIMAL("/takeAnAnimal"),
        RULES("/rules"),
        DOCUMENTATION("/documentationH"),
        RECOMMENDATION("/recommendation"),
            TRANSPORTATION("/transportation"),
            HOME_IMPROVEMENT_YOUNG("/homeImprovementYoung"),
            HOME_IMPROVEMENT_ADULT("/homeImprovementAdult"),
            HOME_IMPROVEMENT_FOR_DISABLED("/homeImprovementForDisabled"),
        DOG_BEHAVIORIST_ADVICE("/dogHandlerAdvice"),
        GET_DOG_BEHAVIORIST("/getDogHandlers"),
        REASONS_FOR_REFUSAL("/reasonsForRefusal"),

    REPORT("/report"),
    CONTACT_DETAILS("/contactDetails"),
    VOLUNTEER("/volunteer");

    private final String command;

    CommandName(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
