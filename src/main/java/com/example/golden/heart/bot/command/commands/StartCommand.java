package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

/**
 * Базавая команда старт.
 */
@Slf4j
public class StartCommand implements Command {
    private TelegramBotSender telegramBotSender;
    private final Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new HashMap<>();
        map.put("c", "/cat");
        map.put("dog", "/dog");
        map.put("info", "/info");
        String message = EmojiParser.parseToUnicode("Привет " + update.message().from().firstName() + " какой приют хочешь выбрать?" + " :blush:");


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }



}
