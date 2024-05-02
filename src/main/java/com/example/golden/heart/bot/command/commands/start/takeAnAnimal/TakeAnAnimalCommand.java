package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class TakeAnAnimalCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public TakeAnAnimalCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new HashMap<>();
        map.put("Правила знакомства с животными перед усыновлением.", "/rules");
        map.put("Список документов, необходимых для того, чтобы взять животное из приюта", "/documentationH");
        map.put("Советы кинолога по первичному общению с собакой.", "/dogHandlerAdvice");  //Для собак
        map.put("Причины, почему могут отказать и не дать забрать животное из приюта.", "/reasonsForRefusal");

        map.put("Рекомендации:", "/recommendation");

        map.put("Принять и записать контактные данные для связи.", "/contactDetails");
        map.put("Позвать волонтера.", "/volunteer");
        map.put("Назад", "/catAndDog");

        String message =
                " Чем помочь?";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
