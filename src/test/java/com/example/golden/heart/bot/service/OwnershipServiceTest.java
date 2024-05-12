package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnershipServiceTest {

    @Mock
    UserService userService;
    @Mock
    PetService petService;
    @Mock
    TelegramBotSender telegramBotSender;

    @InjectMocks
    OwnershipService ownershipService;

    @Test
    void findAllOwnersWithEndedProbation() {
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "Test1", "TestTest1"),
                new User(2L, "Test2", "TestTest2")
        );

        when(userService.findByProbationPeriod(0)).thenReturn(expectedUsers);

        List<User> actualUsers = ownershipService.findAllOwnersWithEndedProbation(); // Вызов тестируемого метода и сохранение результата

        assertNotNull(actualUsers); // Результат не должен быть null
        assertEquals(expectedUsers.size(), actualUsers.size()); // Размеры списков должны совпадать
        assertEquals(expectedUsers, actualUsers); // Списки должны быть идентичны

        verify(userService).findByProbationPeriod(0); // Подтверждение, что метод findByProbationPeriod был вызван с параметром 0
    }

    @Test
    public void increaseProbationPeriodShortTest() {
        Long petId = 1L;
        Long chatId = 12345L;
        User owner = new User();
        owner.setChatId(chatId);
        owner.setProbationPeriod(0); // Установка начального испытательного срока
        Pet pet = new Pet();
        pet.setOwner(owner);
        when(petService.getPetById(petId)).thenReturn(pet);

        ownershipService.increaseProbationPeriod(petId, Increase.SHORT); // Вызов метода увеличения испытательного срока на короткий период

        assertEquals(Integer.valueOf(14), owner.getProbationPeriod()); // Проверка, что испытательный срок увеличен на 14 дней
        verify(telegramBotSender).send(eq(chatId), contains("Ваш испытательный срок продлен на 14 дней"));
    }

    @Test
    public void increaseProbationPeriodLongTest() {
        Long petId = 1L;
        Long chatId = 12345L;
        User owner = new User();
        owner.setChatId(chatId);
        owner.setProbationPeriod(0);
        Pet pet = new Pet();
        pet.setOwner(owner);
        when(petService.getPetById(petId)).thenReturn(pet);

        ownershipService.increaseProbationPeriod(petId, Increase.LONG); // Вызов метода увеличения испытательного срока на длительный период

        assertEquals(Integer.valueOf(30), owner.getProbationPeriod()); // Проверка, что испытательный срок увеличен на 30 дней
        verify(telegramBotSender).send(eq(chatId), contains("Ваш испытательный срок продлен на 30 дней"));
    }


    @Test
    void revokeOwnership() {
        Long petId = 1L;
        Long chatId = 1L;
        User owner = new User();
        owner.setChatId(chatId);
        owner.setRole(Role.USER);
        Pet pet = new Pet(petId, "Test");
        pet.setOwner(owner);
        when(petService.getPetById(petId)).thenReturn(pet);

        ownershipService.revokeOwnership(petId);

        assertNull(owner.getPet()); // Проверка, что у пользователя больше нет питомца
        assertEquals(Role.USER, owner.getRole()); // Проверка, что роль пользователя осталась USER
        verify(telegramBotSender).send(eq(chatId), contains("Вы не прошли испытательный срок. Скоро с Вами свяжется волонтер по вопросу возвращения питомца в приют. Ожидайте"));
    }


    @Test
    void confirmOwnership() {
        Long petId = 1L;
        Long chatId = 1L;
        User owner = new User();
        owner.setChatId(chatId);
        owner.setProbationPeriod(14); // Установка испытательного срока пользователя
        Pet pet = new Pet(petId, "Test"); // Создание нового объекта питомца с идентификатором и именем
        pet.setOwner(owner);
        when(petService.getPetById(petId)).thenReturn(pet); // Мокирование поведения сервиса: при вызове getPetById с petId возвращается объект pet

        ownershipService.confirmOwnership(petId);  // Вызов метода подтверждения владения для petId

        assertNull(owner.getProbationPeriod()); // Проверка, что испытательный срок пользователя теперь равен null
        verify(petService).removePetById(petId); // Проверка, что метод removePetById был вызван для petService с petId
        verify(telegramBotSender).send(eq(chatId), contains("Поздравляем! Вы прошли испытательный срок и теперь является полноценным владельцем питомца")); // Проверка, что метод send был вызван для telegramBotSender с сообщением о прохождении испытательного срока
    }
}