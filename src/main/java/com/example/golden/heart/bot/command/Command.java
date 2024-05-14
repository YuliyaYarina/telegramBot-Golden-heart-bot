package com.example.golden.heart.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

import java.io.IOException;

/**
 * Интерфейс, объединяющий все команды
 * Все команды должны реализировать этот интерфейс
 */
public interface Command {
    /**
     * Обработка отправленных сообщений, и отправление ответа
     */
    void execute(Update update);

}
