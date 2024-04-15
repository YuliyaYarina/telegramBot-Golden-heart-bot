package com.example.golden.heart.bot.command;

import com.example.golden.heart.bot.command.commands.StartCommand;
import com.example.golden.heart.bot.command.commands.InfoCommand;
import com.example.golden.heart.bot.command.commands.UnknownCommand;
//import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.configuration.TelegramBotConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;

@Slf4j
@Component
public class CommandContainer extends TelegramLongPollingBot {

    final TelegramBotConfiguration config;

//    private TelegramBotSender telegramBotSender;

    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities.\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /mydata to see data stored about yourself\n\n" +
            "Type /help to see this message again";

    static final String ERROR_TEXT = "Error occurred: ";

    public CommandContainer(TelegramBotConfiguration config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/help", "info how to use this bot"));
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
        message.setText();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }



    @JsonProperty("inline_keyboard")
    private @NonNull List<List<InlineKeyboardButton>> keyboard;

    /**
     * Этот метод ищет команду внутри map getCommandMap по id команды.
     * @param commandId команды которую оправил пользователь
     * @return возвращает команду совпавший с commandId или UnknownCommand
     */
    public Command findCommand(String commandId) {
        Command unknownCommand = new UnknownCommand(telegramBotSender);
        return getCommandMap().getOrDefault(commandId, unknownCommand);
    }

    private Map<String, Command> getCommandMap() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put(START.getCommand(), new StartCommand(telegramBotSender));
        commandMap.put(INFO.getCommand(), new InfoCommand(telegramBotSender));
        commandMap.put(VOLUNTEER.getCommand(), new InfoCommand(telegramBotSender));

        return commandMap;
    }


//    @JsonProperty("inline_keyboard")
//    public void setKeyboard(@NonNull List<List<InlineKeyboardButton>> keyboard) {
//        if (keyboard == null) {
//            throw new NullPointerException("keyboard is marked non-null but is null");
//        } else {
//            this.keyboard = keyboard;
//        }
//    }
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

//Кнопки бот
//
//catShelter - приют для кошек
//
//DogShelter - приют для собак
//
//
//startInfo - информация о приюте (1)
//takeAnAnimal - как взять животное из приюта (2)
//report - отправить отчет (3)
//volunteer - позвать волонтера.
//
//
//
//        1
//info - информация о приюте.
//        address - расписание работы приюта и адрес, схему проезда.
//security - контактные данные охраны для оформления пропуска на машину.
//safetyPrecautions - общие рекомендации о технике безопасности на территории приюта.
//contactDetails - принять и записать контактные данные для связи.
//        volunteer - позвать волонтера.
//
//        2
//
//rules - правила знакомства с животными перед усыновлением.
//documentation - список документов, необходимых для того, чтобы взять животное из приюта.
//        recommendation - рекомендации
// • transportation - рекомендации по транспортировке животного.
//        • homeImprovementYoung - рекомендаций по обустройству дома для щенка/котенка.
// • homeImprovementAdult- рекомендаций по обустройству дома для взрослого животного.
// • homeImprovementForDisabled - рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение).
//        • provenDogHandlers - рекомендации по проверенным кинологам для дальнейшего обращения к ним. (Для собак)**
//
//dogHandlerAdvice - советы кинолога по первичному общению с собакой. (Для собак)**
//reasonsForRefusal - причины, почему могут отказать и не дать забрать собаку из приюта.
//        contactDetails - принять и записать контактные данные для связи.
//        volunteer - позвать волонтера.
//
//        3
//
//photo - фото. -> diet - питание. -> GeneralHealth - общее самочувствие. -> ChangeIn Behavior - изменение поведения.
//
//volunteer - позвать волонтера.
