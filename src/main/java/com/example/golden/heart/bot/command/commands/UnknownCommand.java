package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.List;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class UnknownCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public UnknownCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Я не понимаю вас. Пожалуйста выберите, что-нибудь из меню:";
        telegramBotSender.sendMessage(message, getChatId(update), setButtons());
    }

    private InlineKeyboardMarkup setButtons() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Информация").callbackData("Информация"),
                new InlineKeyboardButton("Назад").callbackData("Назад"));
    }

}
