package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class InfoCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public InfoCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Основная информация. Помощь";
        telegramBotSender.sendMessage(message, getChatId(update));
    }
}
