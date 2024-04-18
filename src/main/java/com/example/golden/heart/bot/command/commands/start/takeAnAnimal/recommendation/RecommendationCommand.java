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
        map.put("рекомендации по транспортировке животного", "/transportation");
        map.put("рекомендаций по обустройству дома для щенка/котенка", "/homeImprovementYoung");
        map.put("рекомендаций по обустройству дома для взрослого животного", "/homeImprovementAdult");
        map.put("рекомендаций по обустройству дома для животного с ограниченными возможностями)", "/homeImprovementForDisabled");
        map.put("рекомендации по проверенным кинологам для дальнейшего обращения к ним. (Для собак)**", "/provenDogHandlers");  // Для собак

        map.put("позвать волонтера.", "/volunteer");
        map.put("back", "/back");

        String message = "Список рекомендаций:";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
