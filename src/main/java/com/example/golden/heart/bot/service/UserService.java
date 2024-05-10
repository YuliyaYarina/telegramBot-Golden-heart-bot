package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.command.commands.CommandUtils;
import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.listener.TelegramBotUpdateListener;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetService petService;

    @Autowired
    private TelegramBotSender telegramBotSender;

    private CommandUtils commandUtils;
    private final String NO_SUCH_USER = "Пользователь с таким id не найден";
    private final String NO_SUCH_PET = "Питомец с таким id не найден";

    public User save(User user) throws VolunteerAlreadyAppointedException {
        if (user.getRole() == Role.VOLUNTEER){
            checkVolunteer();
        }
        return userRepository.save(user);
    }

    private final Pattern INCOMING_MESSAGE_PATTERN_PHONE_WITH_SPACE = Pattern.compile("\\+\\d{1} \\d{3} \\d{3} \\d{2} \\d{2}");
    private final Pattern INCOMING_MESSAGE_PATTERN_PHONE_WITH_DASH = Pattern.compile("\\+\\d{1}-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    private final String PHONE_ADDED = "Ваш номер успешно принят. Спасибо)";

    public User edit(Long id, User user) throws VolunteerAlreadyAppointedException {
        if (user.getRole() == Role.VOLUNTEER){
        checkVolunteer();
        }
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

    public User changeRole(Long id, Role role) throws VolunteerAlreadyAppointedException {
        User foundUser = getById(id);
        if (foundUser == null) {
            throw new IllegalArgumentException(NO_SUCH_USER);
        }
        if (role == Role.VOLUNTEER) {
            checkVolunteer();
        }
        foundUser.setRole(role);
        return userRepository.save(foundUser);
    }

    public User setPet(Long userId, Long petId) {
        User user = getById(userId);
        Pet pet = petService.getPetById(petId);
        if (user == null) {
            throw new IllegalArgumentException(NO_SUCH_USER);
        }
        if (pet == null){
            throw new IllegalArgumentException(NO_SUCH_PET);
        }
        user.setPet(pet);
        user.setRole(Role.PET_OWNER);
        pet.setOwner(user);
        petService.savePet(pet);
        return userRepository.save(user);
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

        Pattern patternsSpace = INCOMING_MESSAGE_PATTERN_PHONE_WITH_SPACE ;
        Matcher matcherSpace = patternsSpace.matcher(update.message().text());

        Pattern patternDash = INCOMING_MESSAGE_PATTERN_PHONE_WITH_DASH ;
        Matcher matcherDash = patternDash.matcher(update.message().text());

        if (matcherSpace.find()) {
            String phoneNumber = matcherSpace.group().replaceAll(" ", "-");
            logger.info("Приняло новое сообщение: " + update.message().text());

            savePhone(update, phoneNumber);

        } else if (matcherDash.find()){
            String phoneNumber = matcherDash.group();
            logger.info("Приняло новое сообщение: " + update.message().text());

            savePhone(update, phoneNumber);
        }
    }

    private void savePhone(Update update, String phoneNumber){
        Long chatId = update.message().chat().id();
        User user = userRepository.findByChatId(chatId)
                .orElseThrow();
        user.setPhone(phoneNumber);
        userRepository.save(user);

        telegramBotSender.send(chatId, PHONE_ADDED);
    }

    public User findVolunteer() {
        List<User> volunteers = userRepository.findByRole(Role.VOLUNTEER);
        if (!volunteers.isEmpty()) {
            return volunteers.get(0);
        }
            return null;
    }
    public List<User> findByRole(Role role){
        return userRepository.findByRole(role);
    }

    private void checkVolunteer() throws VolunteerAlreadyAppointedException {
        if (findVolunteer() != null) {
            throw new VolunteerAlreadyAppointedException();
        }
    }
}
