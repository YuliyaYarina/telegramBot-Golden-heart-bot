package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

/**
 * Базавая команда старт.
 */
public class StartCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public StartCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Привет Ынукивп";
        telegramBotSender.sendMessage(message, getChatId(update));
    }
}
