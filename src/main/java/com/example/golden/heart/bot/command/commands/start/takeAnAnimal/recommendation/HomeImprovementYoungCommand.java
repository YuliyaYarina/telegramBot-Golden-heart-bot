package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class HomeImprovementYoungCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public HomeImprovementYoungCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/recommendation");

        String message = "Рекомендаций по обустройству дома для щенка/котенка\n" +
                "\n" +
                "1.\tНеобходимо обезопасить провода: велика вероятность, что тут сработает принцип «кто не спрятался — я не виноват»:\n" +
                "\n" +
                "2.\tУберите всё, что плохо лежит, и впредь воспитывайте в себе полезную привычку класть вещи на место, повыше и/или в шкаф. В противном случае отвечать за их судьбу будет новый питомец: он с удовольствием съест носок, залезет в пакет с конфетами и утащит болтающееся на дверце полотенце — игра у него такая.\n" +
                "\n" +
                "3.\tНе забудьте про горшочные растения: некоторые из них ядовиты (например, популярные лилейники, драцена, аспарагус, декабрист и спатифиллум), о некоторые можно уколоться (кактусы, алоэ), а третьи просто очень весело раскапывать, разбрасывая комья земли по всей комнате.\n" +
                "\n" +
                "4.\tПристальное внимание — бытовой химии: всё наверх по шкафам и под замок! Шарики от моли использовали пару лет назад и забыли про них? Не забудьте спрятать!\n" +
                "\n" +
                "5.\tОтныне не стоит оставлять еду в открытом доступе: убирайте подальше шоколад, изюм, кофе, авокадо (лучше почитать заранее, какие еще продукты опасны для животных).\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
