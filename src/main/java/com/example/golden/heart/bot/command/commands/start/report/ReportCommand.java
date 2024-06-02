package com.example.golden.heart.bot.command.commands.start.report;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.enums.ReportState;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.service.PetReportService;
import com.example.golden.heart.bot.service.PhotoService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.enums.CommandName.REPORT;

public class ReportCommand implements Command {

    String message;
    private TelegramBot telegramBot;
    private PhotoService photoService;
    private TelegramBotSender telegramBotSender;

    private PetReportService petReportService;

    private UserService userService;

    private ReportStateStorage reportStateStorage;

    public ReportCommand(TelegramBotSender telegramBotSender, PetReportService petReportService,
                         UserService userService, ReportStateStorage reportStateStorage, TelegramBot telegramBot,
                         PhotoService photoService) {
        this.photoService = photoService;
        this.telegramBot = telegramBot;
        this.reportStateStorage = reportStateStorage;
        this.userService = userService;
        this.petReportService = petReportService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {



        Long chatId = getChatId(update);



        if (!checkUserRoleAndPet(chatId)) {
            Map<String,String> map = new LinkedHashMap<>();
            message = "Извините у вас нет питомца. Или возникла кокая та ошибка.\n" +
                    "Если вы приобретали питомца, попробуйте связатся с волонтером";
            map.put("Позвать волонтера", "/volunteer");
            telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
        } if (checkUserRoleAndPet(chatId)) {
            checkUpdateWhenCallbackQuery(update, chatId);
            if (update.message() != null) {
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
            }
            telegramBotSender.send(getChatId(update), message);
        }

    }

    private void checkUpdateWhenCallbackQuery(Update update, Long chatId) {
        if (update.message() == null &&
                update.callbackQuery() != null &&
                update.callbackQuery().data().equals(REPORT.getCommand())
        ) {
            startReport(chatId);
        }
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
        petReport.setViewed(false);
        petReportService.editPetReport(petReport.getId(), petReport);

        reportStateStorage.replaceValue(chatId, ReportState.BEHAVIOR);
    }

    private void behaviorReport(Long chatId, Update update) {
        message =
                """
                        Я принял изменение привычек
                        А теперь расскажите об общем самочувствии и привыкание к новому месту
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

    /**
     * Метод обрабатывает изображение, отправленное в чат Бота и создает объект класса Photo с его параметрами
     * @param chatId
     * @param update
     */
    private void photoReport(Long chatId, Update update) {


        PetReport petReport = findReport(chatId);

        if (update.message().photo() != null) {
            message =
                    """
                            Я принял ваш отчет. Хорошого дня.\s
                            Если будут проблемы с отчетом то наш волонтер свяжется с вами
                            """;

            String fileId = update.message().photo()[2].fileId();
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(fileId));
            File file = getFileResponse.file();
            try {
                petReportService.saveReportPhotoBot(petReport.getId(), file);

            } catch (IOException ignored) {
            }

            reportStateStorage.remove(chatId);
        } else {
            message =
                    """
                            Отправьте пожалюста фото животного 
                            """;
            reportStateStorage.replaceValue(chatId, ReportState.PHOTO);
        }

    }

    private Boolean checkUserRoleAndPet(Long chatId) {
        User user = userService.findByChatId(chatId);
        return user.getRole().equals(Role.PET_OWNER) && user.getPet() != null;
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
