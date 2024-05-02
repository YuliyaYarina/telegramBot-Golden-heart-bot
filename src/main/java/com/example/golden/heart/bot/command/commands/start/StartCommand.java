package com.example.golden.heart.bot.command.commands.start;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

/**
 * Базавая команда старт.
 */
//@Slf4j
public class StartCommand implements Command {
    private TelegramBotSender telegramBotSender;
    private UserService userService;

    public StartCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {

        Map<String, String> map = new HashMap<>();
        map.put("Приют для кошек", "/catAndDog");
        map.put("Приют для собак", "/catAndDog");

        String message = EmojiParser.parseToUnicode("Привет " + update.message().from().firstName() + " какой приют хочешь выбрать?" + " :blush:");

        if (userService.getById(update.message().from().id()) == null)
            userService.save(new User(update.message().from().id(), update.message().from().firstName(), update.message().from().username()));

        /**
         *  возможно нужно добавить метод, по выбору только 1 раз или кошачий приют или собачий.
         */

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
