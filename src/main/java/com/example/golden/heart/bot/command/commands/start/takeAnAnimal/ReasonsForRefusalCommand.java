package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ReasonsForRefusalCommand implements Command {

        private TelegramBotSender telegramBotSender;

        public ReasonsForRefusalCommand(TelegramBotSender telegramBotSender) {
            this.telegramBotSender = telegramBotSender;
        }

        @Override
        public void execute(Update update) {
            Map<String,String> map = new HashMap<>();
            map.put("Назад", "/takeAnAnimal");

            String message = "Причины, почему могут отказать и не дать забрать животное из приюта.\n" +
                   "\n" +
                    "Волонтеры и сотрудники приютов вкладывают в спасение, лечение, содержание и социализацию каждого своего подопечного очень много сил. Справедливо, что и потенциальных хозяев ждет анкета, собеседование, личное знакомство с кошкой или собакой. Разбираемся, почему некоторым кандидатам отказывают.\n" +
                    "\n" +
                    "✖ Отказ обеспечить безопасность питомца на новом месте\n" +
                    "\n" +
                    "✖ Нестабильные отношения в семье\n" +
                    "\n" +
                    "✖ Антинаучное мышление\n" +
                    "\n" +
                    "✖ Наличие дома большого количества животных\n" +
                    "\n" +
                    "✖ Маленькие дети в семье\n" +
                    "\n" +
                    "✖ Аллергия\n" +
                    "\n" +
                    "✖ Животное забирают в подарок кому-то\n" +
                    "\n" +
                    "✖ Животное забирают в целях использования его рабочих качеств\n" +
                    "\n" +
                    "✖ Отказ приехать познакомиться с животным\n" +
                    "\n" +
                    "✖ Претендент — пожилой человек, проживающий один\n" +
                    "\n" +
                    "✖ Отсутствие регистрации и собственного жилья или его несоответствие нормам приюта\n" +
                    "\n" +
                    "✖ Без объяснения причин\n" +
                    "\n" +
                    "Стать хозяином питомца — большая ответственность. Взять кошку или собаку из приюта — ответственность вдвойне: ведь они уже пережили много несчастий и нуждаются в тепле и заботе. Отказ волонтеров — всего лишь сигнал: что-то в вашем отношении к животным вызывает вопросы, надо их решить. В большинстве случаев кураторы стараются помочь, объяснить, проконсультировать претендентов.\n";

            telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
        }
}
