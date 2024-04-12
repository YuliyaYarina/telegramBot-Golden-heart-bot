package com.example.golden.heart.bot.command.commands;

import com.pengrad.telegrambot.model.Update;

/**
 * Методы, которые используются очень часто и в разных классах
 * getChatId - возвращает id текущего чата
 */
public class CommandUtils {

    public static Long getChatId(Update update) {
        return update.message().chat().id();
    }
}
