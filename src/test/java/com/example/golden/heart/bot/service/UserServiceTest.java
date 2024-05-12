package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static com.example.golden.heart.bot.constants.Constants.USER_1;
import static com.example.golden.heart.bot.constants.Constants.VOLUNTEER;
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
    void save() throws VolunteerAlreadyAppointedException {

        when(userRepository.save(any())).thenReturn(USER_1);

        User userSave = userService.save(USER_1);
        assertEquals(USER_1, userSave);

        verify(userRepository).save(any());
    }

    @Test
    void edit() throws VolunteerAlreadyAppointedException {
//        Give
        User excepted = new User(USER_1.getId(),111L, Role.USER, "111", "Edited", "Edited");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(userRepository.save(excepted)).thenReturn(excepted);

        User result = userService.edit(USER_1.getId(), excepted);

        assertEquals(excepted, result);
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
    void changeRoleTest() throws VolunteerAlreadyAppointedException {
//        Given
        User excepted = USER_1;
        excepted.setRole(Role.PET_OWNER);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(USER_1));
        when(userRepository.save(excepted)).thenReturn(excepted);
//        When
        User actual = userService.changeRole(USER_1.getId(), Role.PET_OWNER);
//        Then
        assertEquals(excepted, actual);
    }

    @Test
    void findByChatIdTest() {
//        Given
        when(userRepository.findByChatId(anyLong())).thenReturn(Optional.ofNullable(USER_1));
//        When
        User actual = userService.findByChatId(USER_1.getChatId());
//        Then
        assertEquals(USER_1, actual);
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

    @Test
    void findVolunteer() {
        List<User> users = new ArrayList<>();
        users.add(VOLUNTEER);

        when(userRepository.findByRole(Role.VOLUNTEER)).thenReturn(users);

//        When
        User actual = userService.findVolunteer();

//        Then
        assertEquals(VOLUNTEER, actual);
    }

    @Test
    void findByRoleTest() {
//        Given
        List<User> users = new ArrayList<>();
        users.add(USER_1);
        when(userRepository.findByRole(any())).thenReturn(users);
//        When
        List<User> actual = userService.findByRole(Role.USER);
//        Then
        assertEquals(users, actual);
    }

    @Test
    void findByProbationPeriod() {
//        Given
        User user = USER_1;
        user.setProbationPeriod(30);
        List<User> excepted = new ArrayList<>();
        excepted.add(user);

//        When
        when(userRepository.findByProbationPeriod(anyInt())).thenReturn(excepted);
        List<User> actual = userService.findByProbationPeriod(user.getProbationPeriod());
//        Then
        assertEquals(excepted, actual);
    }

    @Test
    void wheVolunteerAlreadyAppointedException() {
//        Given
        List<User> volunteer = new ArrayList<>();
        volunteer.add(VOLUNTEER);
        when(userRepository.findByRole(any())).thenReturn(volunteer);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(VOLUNTEER));

//        Save
        assertThrows(
                VolunteerAlreadyAppointedException.class,
                () -> userService.save(VOLUNTEER)
        );
//        Edite
        assertThrows(
                VolunteerAlreadyAppointedException.class,
                () -> userService.edit(VOLUNTEER.getId(), VOLUNTEER)
        );

        assertThrows(
                VolunteerAlreadyAppointedException.class,
                () -> userService.changeRole(1L, Role.VOLUNTEER)
        );
    }
}