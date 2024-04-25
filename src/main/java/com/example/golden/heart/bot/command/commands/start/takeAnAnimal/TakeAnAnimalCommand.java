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
        map.put("правила знакомства с животными перед усыновлением.", "/rules");
        map.put("список документов, необходимых для того, чтобы взять животное из приюта", "/documentationH");
        map.put("рекомендации:", "/recommendation");
//                • transportation - рекомендации по транспортировке животного.
//                • homeImprovementYoung - рекомендаций по обустройству дома для щенка/котенка.
//                • homeImprovementAdult- рекомендаций по обустройству дома для взрослого животного.
//                • homeImprovementForDisabled - рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение).
//                • provenDogHandlers - рекомендации по проверенным кинологам для дальнейшего обращения к ним. (Для собак)**
//
        map.put("советы кинолога по первичному общению с собакой.", "/dogHandlerAdvice");  //Для собак
        map.put("причины, почему могут отказать и не дать забрать животное из приюта.", "/reasonsForRefusal");

        map.put("принять и записать контактные данные для связи.", "/contactDetails");
        map.put("позвать волонтера.", "/volunteer");
        map.put("Назад", "/catAndDog");

        String message =
                " Чем помочь?";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
