package com.example.golden.heart.bot.command.commands.start.report;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ReportCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public ReportCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        // Нужно
        //photo - фото. -> diet - питание. -> GeneralHealth - общее самочувствие. -> ChangeIn Behavior - изменение поведения.

        Map<String,String> map = new HashMap<>();
        map.put("Позвать волонтера", "/volunteer");
        map.put("Назад", "/CatOrDog");

        String message =
                " Жду фото-отчет";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }

}
