package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class StopCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public StopCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Пока";
        telegramBotSender.sendMessage(message, getChatId(update));
    }
}
