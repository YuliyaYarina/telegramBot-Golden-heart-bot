package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class RecommendationCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public RecommendationCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("... транспортировке животного", "/transportation");
        map.put("... обустройству дома для щенка/котенка", "/homeImprovementYoung");
        map.put("... обустройству дома для взрослого животного", "/homeImprovementAdult");
        map.put("... обустройству дома для животного с ограниченными возможностями", "/homeImprovementForDisabled");
        map.put("... проверенным кинологам для дальнейшего обращения к ним. (Для собак)**", "/provenDogHandlers");  // Для собак

        map.put("позвать волонтера.", "/volunteer");
        map.put("Назад", "/takeAnAnimal");

        String message = "Список рекомендаций по... ";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}