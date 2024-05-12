package com.example.golden.heart.bot.command.commands.start.report;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.enums.ReportState;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.service.PetReportService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ReportCommand implements Command {

    String message;
    private TelegramBotSender telegramBotSender;

    private PetReportService petReportService;

    private UserService userService;

    private ReportStateStorage reportStateStorage;

    public ReportCommand(TelegramBotSender telegramBotSender, PetReportService petReportService,
                         UserService userService, ReportStateStorage reportStateStorage) {
        this.reportStateStorage = reportStateStorage;
        this.userService = userService;
        this.petReportService = petReportService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        Map<String,String> map = new LinkedHashMap<>();

        Long chatId = getChatId(update);

        if (!checkUserRoleAndPet(chatId)) {
            message = "Извините у вас нет питомца. Или возникла кокая та ошибка.\n" +
                    "Если вы приобретали питомца, попробуйте связатся с волонтером";
            map.put("Позвать волонтера", "/volunteer");
        }

        ReportState state = reportStateStorage.getValue(chatId);

        if (state == null) {
            reportStateStorage.setValue(chatId, ReportState.START);
            state = reportStateStorage.getValue(chatId);
        }

        switch (state) {
            case START -> startReport(chatId);
            case DIET -> dietReport(chatId, update);
            case PHOTO -> photoReport(chatId, update);
            case BEHAVIOR -> behaviorReport(chatId, update);
            case WELL_BEING -> wellBeingReport(chatId, update);
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

        reportStateStorage.setValue(chatId, ReportState.DIET);
    }

    private void dietReport(Long chatId, Update update) {
        message =
                """
                        Я принял диету питомца а теперь расскажите было ли\s
                        изменения в поведении животного,
                        отказ от старых привычек и приобретение новых
                        """;

        PetReport petReport = findReport(chatId);
        petReport.setDiet(update.message().text());
        petReportService.editPetReport(petReport.getId(), petReport);

        reportStateStorage.replaceValue(chatId, ReportState.BEHAVIOR);
    }

    private void behaviorReport(Long chatId, Update update) {
        message =
                """
                        Я принял изменение привычек
                        А теперь расскажите о Общее самочувствие и привыкание к новому месту
                        """;

        PetReport petReport = findReport(chatId);
        petReport.setBehaviourChange(update.message().text());
        petReportService.editPetReport(petReport.getId(), petReport);

        reportStateStorage.replaceValue(chatId, ReportState.WELL_BEING);
    }


    private void wellBeingReport(Long chatId, Update update) {
        message =
                """
                        Я Принял отчет о самочувствие.
                        А Теперь отправьте фото питомца
                        """;

        PetReport petReport = findReport(chatId);
        petReport.setWellBeing(update.message().text());
        petReportService.editPetReport(petReport.getId(), petReport);

        reportStateStorage.replaceValue(chatId, ReportState.PHOTO);
    }

    private void photoReport(Long chatID, Update update) {
        message =
                """
                        Я принял ваш отчет. Хорошого дня.\s
                        Если будут проблемы с отчетом то наш волонтер свяжется с вами
                        """;

        reportStateStorage.remove(chatID);
    }

    private Boolean checkUserRoleAndPet(Long chatId) {
        User user = userService.findByChatId(chatId);
        return !user.getRole().equals(Role.VOLUNTEER) && user.getPet() != null;
    }


    private PetReport findReport(Long chatId) {
        User user = userService.findByChatId(chatId);

        PetReport petReport = petReportService.findByPetIdAndDate(user.getPet().getId(), LocalDate.now());

        if (petReport == null) {
            petReport = new PetReport();
            petReport.setPet(user.getPet());
            petReport.setDate(LocalDate.now());
            return petReportService.savePetReport(petReport);
        }
        return petReportService.findByPetIdAndDate(user.getPet().getId(), LocalDate.now());
    }
}
