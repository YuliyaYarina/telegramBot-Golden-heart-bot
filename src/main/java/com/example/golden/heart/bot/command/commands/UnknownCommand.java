package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class UnknownCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public UnknownCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Я не понимаю вас. Пожалуйста нажмите /info";
        telegramBotSender.sendMessage(message, getChatId(update));
    }
}
