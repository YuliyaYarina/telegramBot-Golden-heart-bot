package com.example.golden.heart.bot.command;

import com.example.golden.heart.bot.command.commands.*;
import com.example.golden.heart.bot.command.commands.start.*;
import com.example.golden.heart.bot.command.commands.start.info.ContactDetailsCommand;
import com.example.golden.heart.bot.command.commands.start.info.InfoCommand;
import com.example.golden.heart.bot.command.commands.start.info.UnknownCommand;
import com.example.golden.heart.bot.command.commands.start.info.VolonterCommand;
import com.example.golden.heart.bot.command.commands.start.report.ReportCommand;
import com.example.golden.heart.bot.command.commands.start.startInfo.*;
import com.example.golden.heart.bot.command.commands.start.takeAnAnimal.*;
import com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation.*;
import com.example.golden.heart.bot.service.TelegramBotSender;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

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

        commandMap.put(CAT.getCommand(), new CatOrDogCommand(telegramBotSender));
        commandMap.put(DOG.getCommand(), new CatOrDogCommand(telegramBotSender));

        commandMap.put(STARTINFO.getCommand(), new StartInfoCommand(telegramBotSender));
           commandMap.put(ADDRESS.getCommand(), new AddressCommand(telegramBotSender));
           commandMap.put(SECURITY.getCommand(), new SecurityCommand(telegramBotSender));
           commandMap.put(SAFETYPRECAUTIONS.getCommand(), new SafetyPrecautionsCommand(telegramBotSender));

        commandMap.put(TAKEANANIMAL.getCommand(), new TakeAnAnimalCommand(telegramBotSender));
           commandMap.put(RULES.getCommand(), new RulesCommand(telegramBotSender));
           commandMap.put(DOCUMENTATION.getCommand(), new DocumentationCommand(telegramBotSender));
           commandMap.put(RECOMMENDATION.getCommand(), new RecommendationCommand(telegramBotSender));
              commandMap.put(TRANSPORTATION.getCommand(), new TransportationCommand(telegramBotSender));
              commandMap.put(HOMEIMPROVEMENTYOUNG.getCommand(), new HomeImprovementYoungCommand(telegramBotSender));
              commandMap.put(HOMEIMPROVEMENTABULT.getCommand(), new HomeImprovementAdultCommand(telegramBotSender));
              commandMap.put(HOMEIMPROVEMENTFORDISABLED.getCommand(), new HomeImprovementForDisabledComand(telegramBotSender));
              commandMap.put(PROVENDOGHANDLERS.getCommand(), new ProvenDogHandlersCommand(telegramBotSender));
           commandMap.put(DOGHANDLERADVICE.getCommand(), new DogHandlerAdviceCommand(telegramBotSender));
           commandMap.put(REASONEFORREFUSAL.getCommand(), new ReasonsForRefusalCommand(telegramBotSender));

        commandMap.put(REPORT.getCommand(), new ReportCommand(telegramBotSender));

        commandMap.put(INFO.getCommand(), new InfoCommand(telegramBotSender));
        commandMap.put(CONTACTDETAILS.getCommand(), new ContactDetailsCommand(telegramBotSender));
        commandMap.put(VOLUNTEER.getCommand(), new VolonterCommand(telegramBotSender));
        commandMap.put(BACK.getCommand(), new BackCommand(telegramBotSender));


        return commandMap;
    }

}


