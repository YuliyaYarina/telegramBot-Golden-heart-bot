package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class SafetyPrecautionsCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public SafetyPrecautionsCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("назад", "/startInfo");

        String message = "общие рекомендации о технике безопасности на территории приюта.\n" +
                "\tЗапрещается: \n" +
                "•\tСамостоятельно открывать выгулы и вольеры без разрешения работника приюта.\n" +
                "•\tКормить животных. Этим Вы можете спровоцировать драку. Угощения разрешены только постоянным опекунам и волонтерам, во время прогулок с животными на поводке.\n" +
                "•\tОставлять после себя мусор на территории приюта и прилегающей территории.\n" +
                "•\tПодходить близко к вольерам и гладить собак через сетку на выгулах. Животные могут быть агрессивны!\n" +
                "•\tКричать, размахивать руками, бегать между будками или вольерами, пугать и дразнить животных.\n" +
                "•\tПосещение приюта для детей дошкольного и младшего школьного возраста без сопровождения взрослых.\n" +
                "•\tНахождение на территории приюта детей среднего и старшего школьного возраста без сопровождения взрослых или письменной справки-разрешения от родителей или законных представителей.\n" +
                "•\tСамостоятельно заходить в кошатник без разрешения сотрудников приюта.\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
