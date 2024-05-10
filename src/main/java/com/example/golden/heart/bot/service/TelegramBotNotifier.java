package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.PetReportRepository;
import com.example.golden.heart.bot.repository.PetRepository;
import com.example.golden.heart.bot.repository.UserRepository;
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
            if (reports.isEmpty() && pet.getOwner().getRole() != Role.VOLUNTEER) {
                User owner = pet.getOwner();
                telegramBotSender.sendMessage("Сегодня от Вас не поступил отчет о состоянии питомца.\n" +
                        "\t Просьба срочно отправить", pet.getOwner().getChatId());
                reports = petReportRepository.findAllByDateAndPet(date.minusDays(2), pet);
                if (reports.isEmpty()) {
                    User user = userService.findVolunteer();
                    if (user != null)
                        telegramBotSender.sendMessage("Владелец питомца " +
                                pet.getNick() + " с username " + owner.getUserName() + " не отправлял отчет уже более 2 дней", user.getChatId());
                }
            }
        });
    }
}
