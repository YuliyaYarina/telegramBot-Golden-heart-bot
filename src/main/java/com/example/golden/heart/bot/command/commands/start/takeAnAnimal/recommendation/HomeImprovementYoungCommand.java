package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.enums.CommandName.CAT;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class HomeImprovementYoungCommand implements Command {

    private UserService userService;
    private TelegramBotSender telegramBotSender;

    private final String messageForCat = """
            Рекомендаций по обустройству дома для котенка:

            1.\tНеобходимо обезопасить провода: велика вероятность, что тут сработает принцип «кто не спрятался — я не виноват»:
            2.\tБезопасное пространство: Обеспечьте безопасное место для вашего котенка, где он сможет спокойно играть и отдыхать без опасности для себя
            3.\tМесто для отдыха и сна: Предоставьте вашему котенку мягкое место для сна и отдыха4.\tЛоток для туалета: Обеспечьте котенку лоток для туалета в безопасном и легкодоступном месте. Регулярно чистите лоток, чтобы поддерживать гигиеничность
            5.\tКогтеточка: Котята естественно затачивают когти, поэтому важно предоставить им когтеточку, чтобы они могли делать это без повреждения мебели или обивки
            6.\tВнимание и забота: Не забывайте, что ваш котенок нуждается в вашем внимании и заботе.\s""";

    private final String messageForDog = """
            Рекомендаций по обустройству дома для щенка
            1\tБезопасное пространство: Убедитесь, что ваш дом безопасен для щенка.
             Уберите опасные предметы с его пути и защитите электрические провода.
            2.\tМесто для отдыха: Предоставьте щенку мягкое и уютное место для сна и отдыха.
            3.\tМесто для обучения и игр: Выделите в доме специальное место для обучения и игр.
            4.\tМиска для еды и вода: Разместите миски для еды и воды в доступном для щенка месте.
            5.\tИгрушки и жевательные предметы: Обеспечьте щенка разнообразными игрушками и жевательными предметами.
            6.\tВнимание и забота: Не забывайте о внимании и заботе о вашем щенке""";


    public HomeImprovementYoungCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.telegramBotSender = telegramBotSender;
        this.userService = userService;
    }


    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/recommendation");

        telegramBotSender.sendMessage(collectMessage(update), getChatId(update), telegramBotSender.setButtons(map));
    }

    private String collectMessage(Update update) {
         User user = userService.findByChatId(getChatId(update));
        if (user.getChosenPetType().equals(CAT.getCommand())) {
            return messageForCat;
        } else {
            return messageForDog;
        }
    }

}
