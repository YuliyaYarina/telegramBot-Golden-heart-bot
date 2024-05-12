package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void save() throws VolunteerAlreadyAppointedException {
        User user = new User();
        user.setName("Михаил");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User saveUser = userService.save(user);

        Assertions.assertEquals(user, saveUser);
    }

    @Test
    void edite() throws VolunteerAlreadyAppointedException {
        User user = new User();
        user.setName("Михаил");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User editeUser = userService.edit(1L, user);

        Assertions.assertEquals(user, editeUser);
    }

    @Test
    void getById() {
        User user = new User();
        user.setName("Михаил");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User userById = userService.getById(1L);

        Assertions.assertEquals(user.getName(), userById.getName());
    }

    @Test
    void removeById() {
        
    }
}