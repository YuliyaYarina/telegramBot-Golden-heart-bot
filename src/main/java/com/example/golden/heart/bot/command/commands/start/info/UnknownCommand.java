package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class UnknownCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public UnknownCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("информация о приюте (1)", "/startInfo");
        map.put("как взять животное из приюта (2)", "/takeAnAnimal");
        map.put("отправить отчет (3)", "/report");

        map.put("info", "/info");
        map.put("позвать волонтера.", "/volunteer");

        String message = "Я не понимаю вас. Пожалуйста выберите, что-нибудь из меню:";
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
