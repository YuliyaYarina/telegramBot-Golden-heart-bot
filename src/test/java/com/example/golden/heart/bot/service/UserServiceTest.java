package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.constants.Constants;
import com.example.golden.heart.bot.exception.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void save() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User userSave = userService.save(user);
        assertEquals(user, userSave);

        verify(userRepository).save(user);
    }

    @Test
    void edit() {

        Long id = Constants.USER_1.getId();
        User existingUser = Constants.USER_1;

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(eq(existingUser))).thenAnswer(invocation -> invocation.getArgument(0));

        User updateUser = new User();
        updateUser.setName("Обновленное Имя");
        updateUser.setChatId(987654321L);
        updateUser.setRole(Role.USER);
        updateUser.setPhone(7654321);

        User result = userService.edit(id, updateUser);

        assertNotNull(result);
        assertEquals("Обновленное Имя", result.getName());
        assertEquals(987654321L, result.getChatId());
        assertEquals(Role.USER, result.getRole());
        assertEquals(7654321L, result.getPhone());

        verify(userRepository).save(existingUser);
    }


    @ParameterizedTest
    @MethodSource("provideUserForGetById")
    public void getById(Long id, User expectedUser) {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(expectedUser));

        User actualUser = userService.getById(id);

        assertEquals(expectedUser, actualUser, "Возвращаемое значение не равно ожидаемому");

        verify(userRepository).findById(id);
    }

    private static Stream<Arguments> provideUserForGetById() {
        return Stream.of(
                Arguments.of(1L, new User(1L, "Михаил", "Miha1L")),
                Arguments.of(2L, null)
        );

    }


    @Test
    void changeRole_UserNotFound_ThrowsIllegalArgumentException() {
        String userName = "nonexistent";
        Role role = Role.VOLUNTEER;

        when(userRepository.findByUserName(userName)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.changeRole(userName, role);
        });
    }

    @Test
    void changeRole_VolunteerAlreadyAppointed_ThrowsVolunteerAlreadyAppointedException() {
        String userName = "user";
        Role role = Role.VOLUNTEER;

        User volunteer = new User();
        volunteer.setName("volunteer");
        volunteer.setRole(Role.VOLUNTEER);

        when(userRepository.findByUserName(userName)).thenReturn(new User());
        when(userRepository.findByRole(Role.VOLUNTEER)).thenReturn(Arrays.asList(volunteer));

        assertThrows(VolunteerAlreadyAppointedException.class, () -> {
            userService.changeRole(userName, role);
        });
    }

    @Test
    void changeRole_SuccessfulChange_ReturnsUpdatedUser() {
        Long userId = 1L;
        String userName = "user";
        Role role = Role.VOLUNTEER;

        User mockUser = new User(userId, userName, "password");

        when(userRepository.findByUserName(userName)).thenReturn(mockUser);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User result = null;
        try {
            result = userService.changeRole(userName, role);
        } catch (VolunteerAlreadyAppointedException e) {

        }

        assertNotNull(result);
        assertEquals(role, result.getRole());
        verify(userRepository).save(any(User.class));

    }


    @ParameterizedTest
    @MethodSource("provideDataForFindByChatId")
    public void findByChatId(Long chatId, User expectedUser) {

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.ofNullable(expectedUser));

        User actualUser = userService.findByChatId(chatId);

        assertEquals(expectedUser, actualUser, "Возвращаемое значение не равно ожидаемому");
        verify(userRepository).findByChatId(chatId);
    }

    private static Stream<Arguments> provideDataForFindByChatId() {
        return Stream.of(
                Arguments.of(12345L, new User(12345L, "Михаил", "Miha1L")),
                Arguments.of(67890L, null)
        );
    }

    @Test
    void setChoiceCatOrDogCommand() {
        Long chatIdExists = 1L;
        Long chatIdDoesNotExist = 2L;
        String chosenPet = "Кот";
        User mockUser = new User();
        mockUser.setChatId(chatIdExists);
        when(userRepository.findByChatId(chatIdExists)).thenReturn(Optional.of(mockUser));
        when(userRepository.findByChatId(chatIdDoesNotExist)).thenReturn(Optional.empty());

        userService.setChoiceCatOrDogCommand(chatIdExists, chosenPet);
        assertEquals("Кот", mockUser.getChosenPetType());
        verify(userRepository).save(mockUser);

        try {
            userService.setChoiceCatOrDogCommand(chatIdDoesNotExist, chosenPet);
            fail("Ожидается, что будет выдано исключение Null User.");
        } catch (NullUserException e) {

        }

    }

    @Test
    void removeById() {
        Long userId = 1L;

        assertDoesNotThrow(() -> userService.removeById(userId));

        verify(userRepository, times(1)).deleteById(userId);
    }

    @ParameterizedTest
    @MethodSource("roleProvider")
    void findVolunteerByRole_ReturnsCorrectlyBasedOnRole(Role role, User expectedUser) {

        if (role == Role.VOLUNTEER) {
            when(userRepository.findByRole(role)).thenReturn(Arrays.asList(expectedUser));
        }

        User result = userService.findVolunteerByRole(role);

        if (role == Role.VOLUNTEER) {
            assertEquals(expectedUser, result, "Метод должен вернуть пользователя с ролью VOLUNTEER.");
        } else {
            assertNull(result, "Метод должен вернуть null для ролей, отличных от VOLUNTEER.");
        }
    }

    static Stream<Arguments> roleProvider() {
        return Stream.of(
                Arguments.of(Role.VOLUNTEER, new User()), // Предполагаем, что есть пользователь с ролью VOLUNTEER
                Arguments.of(Role.USER, null) // Для других ролей пользователь не найден
        );
    }
}