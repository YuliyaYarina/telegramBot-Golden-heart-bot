package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.PetReportRepository;
import com.example.golden.heart.bot.repository.PetRepository;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
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
    TelegramBot telegramBot;
    @Autowired
    PetReportRepository petReportRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;

    @Scheduled(cron = "0 0 21 * * *")
    public void checkReports() {
        List<Pet> pets = petRepository.findAll().stream()
                .filter(pet -> pet.getOwner() != null)
                .toList();
        pets.forEach(pet -> {
            List<PetReport> reports = petReportRepository.findAllByDateAndPet(LocalDate.now(), pet);
            if (reports.isEmpty()) {
                User owner = pet.getOwner();
                telegramBot.execute(new SendMessage(pet.getOwner().getChatId(), "Сегодня от Вас не поступил отчет о состоянии питомца.\n" +
                        "\t Просьба срочно отправить"));
                reports = petReportRepository.findAllByDateAndPet(LocalDate.now().minusDays(2), pet);
                if (reports.isEmpty()) {
                    User user = userRepository.findByRole(Role.VOLUNTEER).iterator().next();
                    if (user != null)
                        telegramBot.execute(new SendMessage(user.getChatId(), "Владелец питомца " +
                                pet.getNick() + " с username " + owner.getUserName() + " не отправлял отчет уже более 2 дней"));
                }
            }
        });
    }
}
