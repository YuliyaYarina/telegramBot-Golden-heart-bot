package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class HomeImprovementAdultCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public HomeImprovementAdultCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/recommendation");

        String message = "Рекомендаций по обустройству дома для взрослого животного\n" +
                "\n" +
                "Жизнь с братьями нашими меньшими становится насыщенной, интересной и разнообразной. Однако перед тем как завести четвероногого друга, не все владельцы задумываются о том, что это решение может привести к большим изменениям не только в их жизни, но и в обустройстве жилья.\n" +
                "\n" +
                "Рассказываем, как продумать планировку и интерьер квартиры так, чтобы в ней было комфортно жить не только хозяевам, но и питомцам.\n" +
                "\n" +
                "Домашние животные отличаются высокой активностью, им необходимо постоянно передвигаться из одного помещения в другое. Нельзя просто взять и закрыть питомца в одной комнате. А для того чтобы сделать его передвижение свободным и не вставать по первому зову к дверям, можно сделать потайные ходы в стенах и шкафах. Они позволят четвероногому другу беспрепятственно проникать в комнаты, при этом будут замаскированы, чтобы не портить внешний вид интерьера.\n" +
                "\n" +
                "Одним из самых практичных материалов для дома, в котором есть животные, считается керамогранит. Он достаточно прочный и можно не боятся появления царапин от когтей. При этом лучше не использовать глянцевые варианты, так как скользящая поверхность может привести к травмам питомца, если он будет бегать и резвиться. Стоит учитывать также, что на темном материале шерсть и грязь будут более заметны.\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
