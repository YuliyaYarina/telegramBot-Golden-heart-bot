package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.command.commands.CommandUtils;
import com.example.golden.heart.bot.exception.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.listener.TelegramBotUpdateListener;
import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TelegramBotSender telegramBotSender;

    private CommandUtils commandUtils;
    
    public User save(User user) {
        return userRepository.save(user);
    }

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    private final Pattern INCOMING_MESSAGE_PATTERN_PHONE_WITH_SPACE = Pattern.compile("\\+\\d{1} \\d{3} \\d{3} \\d{2} \\d{2}");
    private final Pattern INCOMING_MESSAGE_PATTERN_PHONE_WITH_DASH = Pattern.compile("\\+\\d{1}-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    private final String PHONE_ADDED = "Ваш номер успешно принят. Спасибо)";


    public User edit(Long id, User user) {
        return userRepository.findById(id)
                .map(foundUser -> {
                    foundUser.setName(user.getName());
                    foundUser.setChatId(user.getChatId());
                    foundUser.setPet(user.getPet());
                    foundUser.setRole(user.getRole());
                    foundUser.setPhone(user.getPhone());
                    return userRepository.save(foundUser);
                }).orElse(null);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User changeRole(String userName, Role role) throws VolunteerAlreadyAppointedException {
        if (userRepository.findByUserName(userName) == null) {
            throw new IllegalArgumentException("Пользователь с таким username не найден");
        }
        if (!userRepository.findByRole(Role.VOLUNTEER).isEmpty() && role == Role.VOLUNTEER) {
            User volunteer = userRepository.findByRole(Role.VOLUNTEER).iterator().next();
            throw new VolunteerAlreadyAppointedException("Ответственный волонтер уже назначен, id " + volunteer.getId() + ", username " + volunteer.getUserName());
        }
        User foundUser = userRepository.findByUserName(userName);
        foundUser.setRole(role);
        return userRepository.save(foundUser);
    }

    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId).orElse(null);
    }

    /**
     * Сохроняет выбор собаки или кошки
     * Вызывается в классе CatOrDogCommand
     * @param chatId chatId пользователя
     * @param chosenPet выбор пользователя
     */
    public void setChoiceCatOrDogCommand(Long chatId, String chosenPet) {
        User user = findByChatId(chatId);

        if (user == null) {
            throw new  NullUserException();
        }

        user.setChosenPetType(chosenPet);
        userRepository.save(user);
    }

    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    public void addedPhone(Update update) {

        Long chatId = update.message().chat().id();

        Pattern patternsSpace = INCOMING_MESSAGE_PATTERN_PHONE_WITH_SPACE ;
        Matcher matcherSpace = patternsSpace.matcher(update.message().text());

        Pattern patternDash = INCOMING_MESSAGE_PATTERN_PHONE_WITH_DASH ;
        Matcher matcherDash = patternDash.matcher(update.message().text());

        if (matcherSpace.find()) {
            String phoneNumber = matcherSpace.group().replaceAll(" ", "-");

            logger.info("Приняло новое сообщение: " + update.message().text());
            telegramBotSender.send(chatId, PHONE_ADDED);
        } else if (matcherDash.find()){

            telegramBotSender.send(chatId, PHONE_ADDED);
        }

    }
    
}
