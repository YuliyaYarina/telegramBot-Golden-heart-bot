package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.enums.CommandName.*;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class TakeAnAnimalCommand implements Command {

    private UserService userService;
    private TelegramBotSender telegramBotSender;

    public TakeAnAnimalCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new LinkedHashMap<>();
        map.put("Правила знакомства с животными перед усыновлением.", "/rules");
        map.put("Список документов, необходимых для того, чтобы взять животное из приюта", "/documentationH");
        map.put("Причины, почему могут отказать и не дать забрать животное из приюта.", "/reasonsForRefusal");
        map.put("Принять и записать контактные данные для связи.", "/contactDetails");
        map.put("Рекомендации:", "/recommendation");
        if (getChosenPetType(update).equals(DOG.getCommand())) {
            map.put("Советы кинолога по первичному общению с собакой.", DOG_BEHAVIORIST_ADVICE.getCommand());
            map.put("Проверенные кинологи", GET_DOG_BEHAVIORIST.getCommand());
        }

        map.put("Позвать волонтера", "/volunteer");
        map.put("Назад", "/cat");

        String message =
                " Чем помочь?";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }

    /**
     * Возвращает выбранный тип животного
     */
    private String getChosenPetType(Update update) {
        User user = userService.findByChatId(getChatId(update));
        return user.getChosenPetType();
    }
}
