package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.PetReportRepository;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivingUnprocessedReportsService {


    private final Logger log = LoggerFactory.getLogger(ReceivingUnprocessedReportsService.class);
    @Autowired
    private PetReportService petReportService;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private PetReportRepository petReportRepository;
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<PetReport> markReportAsViewed(Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport == null) {
            return ResponseEntity.notFound().build();
        }
        petReport.setViewed(true);
        petReportService.savePetReport(petReport);
        return ResponseEntity.ok(petReport);
    }

    public  List<PetReport> getAllPetReports() {
        return petReportRepository.findByIsViewed(false);
    }

    public ResponseEntity<Object> getPetReport(Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petReport);
    }

    public ResponseEntity messageReport(Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        log.info(" petPeport  возвращает - {} ", petReport);
        User user = userRepository.findById(petReport.getPet().getId()).orElse(null);

        log.info(" user = {}" + user);
        telegramBot.execute(new SendMessage(user.getChatId(), "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного."));

        log.info(" отправляет уже сообщение пользователю {}", user.getChatId());
        return ResponseEntity.ok().build();
    }
}
