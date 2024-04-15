package com.example.golden.heart.bot.command.commands;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

/**
 * Базавая команда старт.
 */
public class StartCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public StartCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        String message = "Привет" +update;
        message.setText()
        telegramBotSender.sendMessage(message, getChatId(update));
    }

    private void startCommandReceived(long chatId, String name) {

        String answer = "Hi, " + name + ", nice to meet you!";
        log.info("Replied to user " + name);

        sendMessage(chatId, answer);
    }

    private void register (long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do wan cnopka 2");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var catShelter = new InlineKeyboardButton();

        catShelter.setText("Приют для кошек");
        catShelter.setCallbackData("CAT_SHELTER");
        //catShelter - приют для кошек

//DogShelter - приют для собак
        var dogShelter = new InlineKeyboardButton();

        dogShelter.setText("Приют для собак");
        dogShelter.setCallbackData("DOG_SHELTER");

        rowInLine.add(catShelter);
        rowInLine.add(dogShelter);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

//        telegramBotSender.executeMessage(message);
    }
}
