package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.command.commands.CommandUtils;
import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.listener.TelegramBotUpdateListener;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private PetReportService petReportService;

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

    /**
     * Редактирует информацию о пользователе
     * @param id - id пользователя
     * @param user - изменение состояния чата
     * @return пользователя с изменнеными данными
     * @throws VolunteerAlreadyAppointedException
     */
    public User edit(Long id, User user) throws VolunteerAlreadyAppointedException {
        if (user.getRole() == Role.VOLUNTEER){
        checkVolunteer();
        }
        return userRepository.findById(id)
                .map(foundUser -> {
                    foundUser.setName(user.getName());
                    foundUser.setUserName(user.getUserName());
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

    /**
     * Меняет роль пользователя
     * @param id - id пользователя
     * @param role - роль
     * @return возвращает позьзователя с уже изменноной ролью
     * @throws VolunteerAlreadyAppointedException если уже есть волонтер в БД выбрасывает ошибку

     */
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

    /**
     * изменение роли и привязка животногго
     * @param userId - id пользователя
     * @param petId - id питомца
     * @return пользователь к которому привязали питомеца
     */
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
        user.setProbationPeriod(30);
        pet.setOwner(user);
        petService.savePet(pet);
        PetReport initialReport = new PetReport();
        initialReport.setDate(LocalDate.now());
        initialReport.setPet(pet);
        petReportService.savePetReport(initialReport);
        return userRepository.save(user);
    }

    /**
     * Поиск пользователя
     * @param chatId - id чата
     * @return пользоваткля
     */
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

    /**
     * Удаляет пользователя из БД
     * @param id - id пользователя
     */
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Добавляет номер в БД
     * @param update - изменение состояния чата
     */
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

    /**
     * Сохранение номера в БД
     * @param update - изменение состояния чата
     * @param phoneNumber - номер телефона
     */
    private void savePhone(Update update, String phoneNumber){
        Long chatId = update.message().chat().id();
        User user = userRepository.findByChatId(chatId)
                .orElseThrow();
        user.setPhone(phoneNumber);
        userRepository.save(user);

        telegramBotSender.send(chatId, PHONE_ADDED);
    }

    /**
     * Поиск волонтера в БД
     * @return нойденный волонтер, или NULL
     */
    public User findVolunteer() {
        List<User> volunteers = userRepository.findByRole(Role.VOLUNTEER);
        if (!volunteers.isEmpty()) {
            return volunteers.get(0);
        }
            return null;
    }

    /**
     * Ищет пользователя в БД по роли
     * @param role - роль
     * @return пользователя в БД по роли
     */
    public List<User> findByRole(Role role){
        return userRepository.findByRole(role);
    }

    /**
     * Проверяет есть ли в БД воллонтер
     * @throws VolunteerAlreadyAppointedException если есть пользователь в БД выбрасывает ошибку
     */
    private void checkVolunteer() throws VolunteerAlreadyAppointedException {
        if (findVolunteer() != null) {
            throw new VolunteerAlreadyAppointedException();
        }
    }
    public List<User> findByProbationPeriod(Integer probationPeriod) {
        return userRepository.findByProbationPeriod(probationPeriod);
    }
}
