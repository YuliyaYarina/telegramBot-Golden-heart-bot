package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.List;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class InfoCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public InfoCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Основная информация. Помощь";
        telegramBotSender.sendMessage(message, getChatId(update), setButtons());
    }

    private InlineKeyboardMarkup setButtons() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Позвать волонтера").callbackData("позвать волонтера"),
                new InlineKeyboardButton("Информация о приюте").callbackData("Информация о приюте"),
                new InlineKeyboardButton("Информация о боте").callbackData("Инфо о боте"));
    }
}
