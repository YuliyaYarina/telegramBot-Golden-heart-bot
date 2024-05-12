package com.example.golden.heart.bot.command;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс, объединяющий все команды
 * Все команды должны реализировать этот интерфейс
 */
public interface Command {
    /**
     * Метод для основной логики команды
     */
    void execute(Update update);

}
