package com.example.golden.heart.bot.command;

import com.example.golden.heart.bot.command.commands.start.*;
import com.example.golden.heart.bot.command.commands.start.info.ContactDetailsCommand;
import com.example.golden.heart.bot.command.commands.start.info.InfoCommand;
import com.example.golden.heart.bot.command.commands.start.info.UnknownCommand;
import com.example.golden.heart.bot.command.commands.start.info.VolonterCommand;
import com.example.golden.heart.bot.command.commands.start.report.ReportCommand;
import com.example.golden.heart.bot.command.commands.start.startInfo.*;
import com.example.golden.heart.bot.command.commands.start.takeAnAnimal.*;
import com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation.*;
import com.example.golden.heart.bot.service.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;

@Slf4j
@Component
public class CommandContainer{

    private TelegramBotSender telegramBotSender;
    private UserService userService;
    private DogBehavioristService dogBehavioristService;

    private AnimalShelterService animalShelterService;

    private PhotoService photoService;

    public CommandContainer(TelegramBotSender telegramBotSender, UserService userService,
                            DogBehavioristService dogBehavioristService, AnimalShelterService animalShelterService,
                            PhotoService photoService) {
        this.photoService = photoService;
        this.animalShelterService = animalShelterService;
        this.dogBehavioristService = dogBehavioristService;
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
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
        commandMap.put(START.getCommand(), new StartCommand(telegramBotSender, userService));

           commandMap.put(CAT.getCommand(), new CatOrDogCommand(telegramBotSender, userService));
           commandMap.put(DOG.getCommand(), new CatOrDogCommand(telegramBotSender, userService));

        commandMap.put(START_INFO.getCommand(), new StartInfoCommand(telegramBotSender));
           commandMap.put(ADDRESS.getCommand(), new AddressCommand(telegramBotSender, animalShelterService, photoService));
           commandMap.put(SECURITY.getCommand(), new SecurityCommand(telegramBotSender));
           commandMap.put(SAFETY_PRECAUTIONS.getCommand(), new SafetyPrecautionsCommand(telegramBotSender));

        commandMap.put(TAKE_AN_ANIMAL.getCommand(), new TakeAnAnimalCommand(telegramBotSender, userService));
           commandMap.put(RULES.getCommand(), new RulesCommand(telegramBotSender));
           commandMap.put(DOCUMENTATION.getCommand(), new DocumentationCommand(telegramBotSender));
           commandMap.put(RECOMMENDATION.getCommand(), new RecommendationCommand(telegramBotSender));
              commandMap.put(TRANSPORTATION.getCommand(), new TransportationCommand(telegramBotSender));
              commandMap.put(HOME_IMPROVEMENT_YOUNG.getCommand(), new HomeImprovementYoungCommand(telegramBotSender, userService));
              commandMap.put(HOME_IMPROVEMENT_FOR_DISABLED.getCommand(), new HomeImprovementForDisabledComand(telegramBotSender));
              commandMap.put(HOME_IMPROVEMENT_ADULT.getCommand(), new HomeImprovementAdultCommand(telegramBotSender));
           commandMap.put(DOG_BEHAVIORIST_ADVICE.getCommand(), new DogBehavioristCommand(telegramBotSender, dogBehavioristService));
           commandMap.put(GET_DOG_BEHAVIORIST.getCommand(), new DogBehavioristCommand(telegramBotSender, dogBehavioristService));
           commandMap.put(REASONS_FOR_REFUSAL.getCommand(), new ReasonsForRefusalCommand(telegramBotSender));

        commandMap.put(REPORT.getCommand(), new ReportCommand(telegramBotSender));

        commandMap.put(INFO.getCommand(), new InfoCommand(telegramBotSender));
        commandMap.put(CONTACT_DETAILS.getCommand(), new ContactDetailsCommand(telegramBotSender));
        commandMap.put(VOLUNTEER.getCommand(), new VolonterCommand(telegramBotSender, userService));

        return commandMap;
    }

}


