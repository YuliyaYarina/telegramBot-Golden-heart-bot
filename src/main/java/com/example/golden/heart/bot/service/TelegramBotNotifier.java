package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.PetReportRepository;
import com.example.golden.heart.bot.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
public class TelegramBotNotifier {
    @Autowired
    TelegramBotSender telegramBotSender;
    @Autowired
    PetReportRepository petReportRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserService userService;

    @Scheduled(cron = "0 0 21 * * *")
    public void checkReports() {
        LocalDate date = LocalDate.now();
        List<Pet> pets = petRepository.findAll().stream()
                .filter(pet -> pet.getOwner() != null)
                .toList();
        pets.forEach(pet -> {
            List<PetReport> reports = petReportRepository.findAllByDateAndPet(date, pet);
            User owner = pet.getOwner();
            if (owner.getProbationPeriod() == null) {
                owner.setProbationPeriod(0);
            }
            Integer probationPeriod = owner.getProbationPeriod();
            if (owner.getProbationPeriod() > 0) {
                owner.setProbationPeriod(probationPeriod - 1);
                if (reports.isEmpty()) {
                    telegramBotSender.send(owner.getChatId(), "Сегодня от Вас не поступил отчет о состоянии питомца.\n" +
                            "\t Просьба срочно отправить");
                    reports = petReportRepository.findAllByDateAndPet(date.minusDays(1), pet);
                    if (reports.isEmpty()) {
                        reports = petReportRepository.findAllByDateAndPet(date.minusDays(2), pet);
                        if (reports.isEmpty()) {
                            User volunteer = userService.findVolunteer();
                            if (volunteer != null)
                                telegramBotSender.send(volunteer.getChatId(), "Владелец питомца " +
                                        pet.getNick() + " с username " + owner.getUserName() + " не отправлял отчет уже более 2 дней");
                        }
                    }
                }
            }
        });
    }
}
