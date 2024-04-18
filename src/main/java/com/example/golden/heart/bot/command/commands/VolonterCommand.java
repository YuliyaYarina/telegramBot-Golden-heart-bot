package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class VolonterCommand implements com.example.golden.heart.bot.command.Command {
    private TelegramBotSender telegramBotSender;

    public VolonterCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new HashMap<>();
        map.put("back", "/back");

        /**
         *  нужен Метод по вызову волонтёра
         */

        String message = "Уже позвал волонтёра. Скоро будет";
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
