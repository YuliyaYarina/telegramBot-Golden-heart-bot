package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class InfoCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public InfoCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();


        map.put("Назад", "/catAndDog");
        map.put("позвать волонтера.", "/volunteer");

        String message = "Основная информация. Помощь";
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }

}
