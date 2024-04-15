package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class VolonterCommand implements com.example.golden.heart.bot.command.Command {
    private TelegramBotSender telegramBotSender;

    public VolonterCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Позвать волонтёра. Помощь";
        telegramBotSender.sendMessage(message, getChatId(update), setButtons());
    }

    private InlineKeyboardMarkup setButtons() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Назад").callbackData("Назад"));
    }
}
