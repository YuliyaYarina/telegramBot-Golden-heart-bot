package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class UnknownCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public UnknownCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("Информация о приюте", "/startInfo");
        map.put("Как взять животное из приюта", "/takeAnAnimal");
        map.put("Отправить отчет", "/report");

        map.put("info", "/info");
        map.put("Позвать волонтера.", "/volunteer");

        String message = "Я не понимаю вас. Пожалуйста выберите, что-нибудь из меню:";
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
