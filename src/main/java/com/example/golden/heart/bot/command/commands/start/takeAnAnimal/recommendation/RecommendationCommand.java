package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class RecommendationCommand implements Command {
    private TelegramBotSender telegramBotSender;
    private UserService userService;

    public RecommendationCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("... транспортировке животного", "/transportation");

        if (getChosenPetType(update).equals(DOG.getCommand())) {
            map.put("... обустройству дома для щенка", "/homeImprovementYoung");
        }if (getChosenPetType(update).equals(CAT.getCommand())) {
            map.put("... обустройству дома для котенка", "/homeImprovementYoung");
        }
        map.put("... обустройству дома для взрослого животного", "/homeImprovementAdult");
        map.put("... обустройству дома для животного с ограниченными возможностями", "/homeImprovementForDisabled");
        map.put("позвать волонтера.", "/volunteer");
        map.put("Назад", "/takeAnAnimal");

        String message = "Список рекомендаций по... ";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
    private String getChosenPetType(Update update) {
        User user = userService.findByChatId(getChatId(update));
        return user.getChosenPetType();
    }
}