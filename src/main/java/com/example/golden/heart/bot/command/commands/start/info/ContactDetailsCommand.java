package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ContactDetailsCommand implements Command {
    private TelegramBotSender telegramBotSender;
    private UserService userService;

    public ContactDetailsCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/cat");
/**
 * доработать метод
 */
        String message = "Отправьте ваши контактные данные для связи по форме: +7-9**-***-**-**";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
