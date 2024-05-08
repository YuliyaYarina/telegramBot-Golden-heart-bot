package com.example.golden.heart.bot.command.commands.start.report;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.enums.ReportState;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.PetReportService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ReportCommand implements Command {
    private static HashMap<Long, ReportState> reportState = new HashMap<>();

    String message;
    private TelegramBotSender telegramBotSender;

    private PetReportService petReportService;

    private UserService userService;

    public ReportCommand(TelegramBotSender telegramBotSender, PetReportService petReportService,
                         UserService userService) {
        this.userService = userService;
        this.petReportService = petReportService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new LinkedHashMap<>();
        map.put("Позвать волонтера", "/volunteer");
        map.put("Назад", "/cat");

        Long chatId = getChatId(update);

        ReportState state = reportState.get(chatId);

        if (state == null) {
            startReport(chatId);
        }

        switch (Objects.requireNonNull(state)) {
            case DIET -> dietReport(chatId, update);
            case PHOTO -> photoReport();
            case BEHAVIOR -> behaviorReport();
            case WELL_BEING -> wellBeingReport();
        }


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }

    private void startReport(Long chatId) {
        message =
                """
                        Здравствуйте! Я готов принят ваш отчет
                        Напишите диету питомца что он кушает
                        Например:
                        Завтрак - косточка,
                        Обед - корм,
                        Вечером - молоко
                        """;

        reportState.put(chatId, ReportState.DIET);
    }

    private void dietReport(Long chatId, Update update) {
        message =
                """
                        Я принял диету питомца а теперь расскажите было ли\s
                        изменения в поведении животного,
                        отказ от старых привычек и приобретение новых
                        """;




    }

    private void behaviorReport() {

    }

    private void photoReport() {

    }

    private void wellBeingReport() {

    }

    private PetReport findReport(Long chatId) {
        User user = userService.findByChatId(chatId);
        PetReport petReport = petReportService.findByPetIdAndData(user.getPet().getId(), LocalDate.now());

        if (petReport == null) {

        }
    }
}
