package com.example.golden.heart.bot.listener;

import com.example.golden.heart.bot.command.CommandContainer;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TelegramBotUpdateListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private TelegramBotSender telegramBotSender;

    @Autowired
    CommandContainer commandContainer;

    private final String commandPrefix = "/";

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String messageText = update.message().text();
            if (update.message() != null && messageText.startsWith(commandPrefix)) {
                commandContainer.findCommand(messageText.toLowerCase()).execute(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
