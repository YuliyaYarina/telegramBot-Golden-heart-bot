package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class SecurityCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public SecurityCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("назад", "/startInfo");

        String message = "Контактные данные охраны для оформления пропуска на машину :\n" +
                "\t(3952) 56-04-90, (3952) 600-444";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
