package com.example.golden.heart.bot.command;

import com.pengrad.telegrambot.model.Update;

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
