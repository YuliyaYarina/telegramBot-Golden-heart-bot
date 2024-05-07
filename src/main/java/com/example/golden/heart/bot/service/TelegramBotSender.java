package com.example.golden.heart.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TelegramBotSender {
    @Autowired
    private TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(TelegramBotSender.class);


    /**
     *  Отправляет сообщения к указанному chatId
     */
    public void sendMessage(String messageText, Long chatId, InlineKeyboardMarkup markupInline) {
        SendResponse response = telegramBot.execute(new SendMessage(chatId, messageText).replyMarkup(markupInline));
    }

    public InlineKeyboardMarkup setButtons(Map<String, String> buttons){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Map.Entry<String, String> entry : buttons.entrySet()){
            markup.addRow(new InlineKeyboardButton(entry.getKey()).callbackData(entry.getValue()));
        }
        return markup;
    }

    public void send(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);

        if(response.isOk()) {
            logger.info("Сooбщение успешно отправлено: {}", message);
        } else {
            logger.error("Ошибка при отправке сообщения: {}", response.errorCode());
        }

    }
}
