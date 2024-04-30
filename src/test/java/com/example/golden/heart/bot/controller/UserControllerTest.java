package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void saveUser() {
    }

    @Test
    void editeUser() {
    }

    @Test
    void getUser() {
        User user = new User();
        user.setName("Михаил");
        user.setPhone(7-912-382-54-24);
        user.setRole(Role.USER);
        User save = userRepository.save(user);
        long id = save.getId();
        long chtId = save.getChtId();
        ResponseEntity<User> forEntity = testRestTemplate.getForEntity("/user/" + id + chtId, User.class);
        HttpStatusCode statusCode = forEntity.getStatusCode();
        Assertions.assertEquals(200, statusCode.value());
        assertNotNull(forEntity.getBody());
        Assertions.assertEquals(forEntity.getBody().getName() , user.getName());
        Assertions.assertEquals(forEntity.getBody().getPhone() , user.getPhone());
        Assertions.assertEquals(forEntity.getBody().getRole() , user.getRole());

    }

    @Test
    void removeUser() {
    }
}