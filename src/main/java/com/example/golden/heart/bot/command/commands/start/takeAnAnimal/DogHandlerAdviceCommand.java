package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;
/**
 * Для собак
 */

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class DogHandlerAdviceCommand implements Command {

    private TelegramBotSender telegramBotSender;

    public DogHandlerAdviceCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();

        String message = "советы кинолога по первичному общению с собакой.";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
