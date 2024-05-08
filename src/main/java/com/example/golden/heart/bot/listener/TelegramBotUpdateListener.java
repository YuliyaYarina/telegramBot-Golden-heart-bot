package com.example.golden.heart.bot.listener;

import com.example.golden.heart.bot.command.CommandContainer;
import com.example.golden.heart.bot.command.commands.start.report.ReportCommand;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.enums.CommandName.REPORT;


@Service
public class TelegramBotUpdateListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);


    @Autowired
    private TelegramBotSender telegramBotSender;

    @Autowired
    TelegramBot telegramBot;

    @Autowired
    CommandContainer commandContainer;

    @Autowired
    UserService userService;

    private final String commandPrefix = "/";
    private final String startsPhone = "+7";

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (ReportCommand.reportState.get(getChatId(update)) != null) {
                commandContainer.findCommand(REPORT.getCommand()).execute(update);
            }
            if (update.message() != null && update.message().text().startsWith(commandPrefix)) {
                commandContainer.findCommand(update.message().text().toLowerCase()).execute(update);
            }else {
                if (update.callbackQuery() != null) {
                    commandContainer.findCommand(update.callbackQuery().data()).execute(update);
                }
            }
            if (update.message() != null && update.message().text().startsWith(startsPhone)) {
                logger.info("Сообщение отправлено: " + update.message().text());
                userService.addedPhone(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
