package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class BackCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public BackCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        /**
         * Написать метод по выходу назад
         */


//        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
