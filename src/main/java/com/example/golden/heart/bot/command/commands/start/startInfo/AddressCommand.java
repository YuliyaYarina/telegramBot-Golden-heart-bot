package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class AddressCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public AddressCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }
    /**
     * доработать метод, добавить информацию из БД
     */
    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("назад", "/startInfo");

       String message = "Расписание работы приюта и адрес : \n" +
               "\tг. Астана ул.Карпинская, 125\n" +
               "\tЕжедневно с 8-00 до 18-00,\n" +
               "\t схема проезда: / фото / ";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
