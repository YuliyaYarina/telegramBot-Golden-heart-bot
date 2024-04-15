package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

/**
 * Базавая команда старт.
 */
public class StartCommand implements Command {
    private TelegramBotSender telegramBotSender;
    private final Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Привет" +update;
        telegramBotSender.sendMessage(message, getChatId(update), setButtons());
    }

    private InlineKeyboardMarkup setButtons() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Прият для кошек").callbackData("Приют для кошек"),
                new InlineKeyboardButton("Прият для собак").callbackData("Приют для собак"),
                new InlineKeyboardButton("Назад").callbackData("Назад"));
    }

}
