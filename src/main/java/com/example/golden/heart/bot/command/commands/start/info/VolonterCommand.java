package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
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
        map.put("Назад", "/catAndDog");

        /**
         *  нужен Метод по вызову волонтёра
         */

        String message = "Уже позвал волонтёра. Скоро будет";
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
