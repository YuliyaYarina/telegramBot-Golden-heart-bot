package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;


import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.model.enums.Role.VOLUNTEER;

public class VolonterCommand implements com.example.golden.heart.bot.command.Command {
    String message;
    private TelegramBotSender telegramBotSender;

    private UserService userService;

    public VolonterCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.userService = userService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton("Назад").callbackData("/cat"));

        if (collectVolunteerUrl() != null) {
            markup.addRow(new InlineKeyboardButton("Перейти к чату").url(collectVolunteerUrl()));
            message = "Нажмите кнопку 'Перейти к чату' чтобы связатся c волонтером";
        }

 telegramBotSender.sendMessage(message, getChatId(update), markup);
    }

    private String collectVolunteerUrl() {
        User user = userService.findVolunteer();
        if (user == null) {
            message = "Извините я не смог найти волонтера";
            return null;
        }
        return "http://t.me/" + user.getUserName();
    }
}
