package com.example.golden.heart.bot.command.commands.start;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

@Slf4j
public class CatOrDogCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public CatOrDogCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new LinkedHashMap<>();
        map.put("Информация о приюте", "/startInfo");
        map.put("Как взять животное из приюта", "/takeAnAnimal");
        map.put("Отправить отчет", "/report");

        map.put("Позвать волонтера.", "/volunteer");

        String message =
                " Что хочешь выбрать?";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}