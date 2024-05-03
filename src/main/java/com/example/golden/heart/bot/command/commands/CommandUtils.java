package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.exceptions.UnknownUpdateException;
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
        if (update.message() !=  null){
        return update.message().chat().id();
        } else if (update.callbackQuery() != null) {
            return update.callbackQuery().message().chat().id();
        } else {
            throw new UnknownUpdateException("Не известная update");
        }
}

    public static String getFirstName(Update update) {
        if (update.message() != null) {
            return update.message().from().firstName();
        } else if (update.callbackQuery() != null) {
            return update.callbackQuery().from().firstName();
        } else {
            throw new UnknownUpdateException();
        }
    }

    public static String getUserName(Update update) {
        if (update.message() != null) {
            return update.message().from().username();
        } else if (update.callbackQuery() != null) {
            return update.callbackQuery().from().username();
        } else {
            throw new UnknownUpdateException();
        }
    }
}
