package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.service.AnimalShelterService;
import com.example.golden.heart.bot.service.PhotoService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.enums.CommandName.START_INFO;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class AddressCommand implements Command {
    private String message;
    private Map<String, String> buttons;

    private TelegramBotSender telegramBotSender;
    private AnimalShelterService animalShelterService;
    private PhotoService photoService;

    public AddressCommand(TelegramBotSender telegramBotSender, AnimalShelterService animalShelterService,
                          PhotoService photoService) {
        this.photoService = photoService;
        this.animalShelterService = animalShelterService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        AnimalShelter animalShelter = animalShelterService.getAnimalShelterById(1L);
        collectMessage(animalShelter);
        sendPhoto(update, animalShelter);

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(buttons));
    }

    private void collectMessage(AnimalShelter animalShelter) {
        message = "Расписание работы приюта и адрес : \n" +
                animalShelter.getAddress() + "\n" +
                animalShelter.getWorkSchedule();

        buttons = new LinkedHashMap<>();
        buttons.put("назад", START_INFO.getCommand());
    }

    /**
     *Отправляет фото проезда к приюту
     */
    private boolean sendPhoto(Update update, AnimalShelter animalShelter) {
        Photo foundPhoto = photoService.findPhotoByAnimalShelterId(animalShelter.getId());

        if (foundPhoto.getFilePath() == null) {
            message = message + "\n К сожелению у нас нет фото адреса приюта";
            return false;
        }

        // Создание объекта File из пути к файлу
        File photo = new File(foundPhoto.getFilePath());
        // Отправка фотографии в ответ
        telegramBotSender.sendPhoto(getChatId(update), photo);
        return true;
    }
}
