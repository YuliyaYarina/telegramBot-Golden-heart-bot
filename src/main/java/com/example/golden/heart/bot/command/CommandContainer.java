package com.example.golden.heart.bot.command;

import com.example.golden.heart.bot.command.commands.StartCommand;
import com.example.golden.heart.bot.command.commands.InfoCommand;
import com.example.golden.heart.bot.command.commands.UnknownCommand;
import com.example.golden.heart.bot.configuration.TelegramBotConfiguration;
import com.example.golden.heart.bot.service.TelegramBotSender;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;

@Slf4j
@Component
public class CommandContainer{

    private TelegramBotSender telegramBotSender;
    private StartCommand startCommand;

    public CommandContainer(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

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
        commandMap.put(CAT.getCommand(), new InfoCommand(telegramBotSender));
        commandMap.put(DOG.getCommand(), new InfoCommand(telegramBotSender));
        commandMap.put(BACK.getCommand(), new InfoCommand(telegramBotSender));


        return commandMap;
    }

}


