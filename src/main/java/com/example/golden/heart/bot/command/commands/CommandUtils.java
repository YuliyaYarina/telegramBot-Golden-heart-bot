package com.example.golden.heart.bot.command.commands;

import com.pengrad.telegrambot.model.Update;

/**
 * Методы, которые используются очень часто и в разных классах
 */
public class CommandUtils {

    /**
     * @param update
     * @return возвращает id текущего чата
     */
    public static Long getChatId(Update update) {
        return update.message().chat().id();
    }
}
