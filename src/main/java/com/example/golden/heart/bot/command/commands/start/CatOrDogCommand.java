package com.example.golden.heart.bot.command.commands.start;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class CatOrDogCommand implements Command {
    private TelegramBotSender telegramBotSender;

    private UserService userService;

    public CatOrDogCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
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
        setChoice(update);
    }

    /**
     * Сохраняет в БД выбранный тип животного
     */
    private void setChoice(Update update) {
        String choice = update.callbackQuery().data();
        userService.setChoiceCatOrDogCommand(getChatId(update), choice);
    }
}