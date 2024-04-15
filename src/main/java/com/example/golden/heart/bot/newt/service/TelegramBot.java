package com.example.golden.heart.bot.newt.service;

import com.example.golden.heart.bot.configuration.TelegramBotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final TelegramBotConfiguration config;

    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities.\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /mydata to see data stored about yourself\n\n" +
            "Type /help to see this message again";

    public TelegramBot(TelegramBotConfiguration config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/mydata", "get your data stored"));
        listofCommands.add(new BotCommand("/deletedata", "delete my data"));
        listofCommands.add(new BotCommand("/help", "info how to use this bot"));
        listofCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":

                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/help":

                    sendMessage(chatId, HELP_TEXT);
                    break;
                default:

                    sendMessage(chatId, "Sorry, command was not recognized");

            }
        }


    }

    private void startCommandReceived(long chatId, String name) {

        String answer = "Hi, " + name + ", nice to meet you!";
        log.info("Replied to user " + name);

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}

//
//    private void executeEditMessageText(String text, long chatId, long messageId){
//        EditMessageText message = new EditMessageText();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        message.setMessageId((int) messageId);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            log.error(ERROR_TEXT + e.getMessage());
//        }
//    }
//
//    private void executeMessage(SendMessage message){
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            log.error(ERROR_TEXT + e.getMessage());
//        }
//    }
//
//    private void prepareAndSendMessage(long chatId, String textToSend){
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//        executeMessage(message);
//    }
//
//    @Scheduled(cron = "${cron.scheduler}")
//    private void sendAds(){
//
//        var ads = adsRepository.findAll();
//        var users = userRepository.findAll();
//
//        for(Ads ad: ads) {
//            for (User user: users){
//                prepareAndSendMessage(user.getChatId(), ad.getAd());
//            }
//        }
//
//    }
