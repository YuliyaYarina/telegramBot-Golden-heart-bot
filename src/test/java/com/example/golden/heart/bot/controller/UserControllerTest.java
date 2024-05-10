package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    public void testSaveUser() {
//        When
        ResponseEntity<User> response = testRestTemplate.postForEntity(
                HOST + port + "/user",
                USER_1,
                User.class
        );

        User excepted = USER_1;
        excepted.setId(response.getBody().getId());
//    Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testEditUser() {
//        Given
//        User user = userService.save(USER_1);
//        User editeUser = new User(user.getId(), 222L, Role.USER, 2222, "Edited", "Edited");
//        HttpEntity<User> requestEntity = new HttpEntity<>(editeUser);
//
////        When
//        ResponseEntity<User> response = testRestTemplate.exchange(
//                HOST + port + "/user/" + user.getId(),
//                HttpMethod.PUT,
//                requestEntity,
//                User.class
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(editeUser, response.getBody());
    }

    @Test
    public void testGetUser() {
////        Given
//        User excepted = userService.save(USER_1);
//
////        When
//        ResponseEntity<User> response = testRestTemplate.getForEntity(
//                HOST + port + "/user/" + excepted.getId(),
//                User.class
//        );
//
////        Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeleteUser() {
//        Given
//        User excepted = userService.save(USER_1);
//
////        When
//        ResponseEntity<User> response = testRestTemplate.exchange(
//                HOST + port + "/user/" + excepted.getId(),
//                HttpMethod.DELETE,
//                null,
//                User.class
//        );
//
//        User afterDelete = userService.getById(excepted.getId());
//
////        Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(excepted, response.getBody());
//
//        assertNull(afterDelete);
    }

    @Test
    public void testWhenUserNotFound() {
//        User user = new User(444L, 1L, Role.USER, 22, "TEST", "TEST");
//        HttpEntity<User> requestEntity = new HttpEntity<>(user);
////        When
//        ResponseEntity<User> editeResponse = testRestTemplate.exchange(
//                HOST + port + "/user/" + user.getId(),
//                HttpMethod.PUT,
//                requestEntity,
//                User.class
//        );
//
//        ResponseEntity<User> getResponse = testRestTemplate.getForEntity(
//                HOST + port + "/user/" + user.getId(),
//                User.class
//        );
//
//        ResponseEntity<User> deleteResponse = testRestTemplate.exchange(
//                HOST + port + "/user/" + user.getId(),
//                HttpMethod.DELETE,
//                null,
//                User.class
//        );
//
////        Then
//        assertEquals(HttpStatus.NOT_FOUND, editeResponse.getStatusCode());
//        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
//        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }
}