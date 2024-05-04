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

import static com.example.golden.heart.bot.command.commands.CommandUtils.*;

/**
 * Базавая команда старт.
 */
@Slf4j
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
        map.put("Приют для кошек", "/cat");
        map.put("Приют для собак", "/dog");

        String message = EmojiParser.parseToUnicode("Привет " + update.message().from().firstName() + " какой приют хочешь выбрать?" + " :blush:");

        if (userService.findByChatId(getChatId(update)) == null) {
            userService.save(new User(getChatId(update), getFirstName(update), getUserName(update)));
        }


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
