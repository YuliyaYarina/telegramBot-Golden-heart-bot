package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.PetService;
import com.example.golden.heart.bot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    PetService petService;

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
    public void testEditUser()throws VolunteerAlreadyAppointedException {
//        Given
        User user = userService.save(USER_1);
        User editeUser = new User(user.getId(), 222L, Role.USER, "222", "Edited", "Edited");
        HttpEntity<User> requestEntity = new HttpEntity<>(editeUser);

//        When
        ResponseEntity<User> response = testRestTemplate.exchange(
                HOST + port + "/user/" + user.getId(),
                HttpMethod.PUT,
                requestEntity,
                User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editeUser, response.getBody());
    }

    @Test
    public void testGetUser() throws VolunteerAlreadyAppointedException {
//        Given
        User excepted = userService.save(USER_1);

//        When
        ResponseEntity<User> response = testRestTemplate.getForEntity(
                HOST + port + "/user/" + excepted.getId(),
                User.class
        );

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeleteUser() throws VolunteerAlreadyAppointedException {
//        Given
        User excepted = userService.save(USER_1);

//        When
        ResponseEntity<User> response = testRestTemplate.exchange(
                HOST + port + "/user/" + excepted.getId(),
                HttpMethod.DELETE,
                null,
                User.class
        );

        User afterDelete = userService.getById(excepted.getId());

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

        assertNull(afterDelete);
    }

    @Test
    public void testChangeRole() throws VolunteerAlreadyAppointedException {
//        Given
        User user = userService.save(USER_1);
        String excepted = "Роль пользователя " + user.getUserName() + " успешно изменена";
//        When
        ResponseEntity<String> response = testRestTemplate.exchange(
                HOST + port + "/user/change-role?id=" + user.getId() + "&role=" + Role.PET_OWNER,
                HttpMethod.PUT,
                null,
                String.class
        );


//        Then

        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testSetPet() throws VolunteerAlreadyAppointedException {
//        Given
        User user = userService.save(USER_1);
        Pet pet = petService.savePet(PET_1);
        String excepted = "Пользователь " + user.getUserName() + " теперь является владельцем питомца";
//        When
        ResponseEntity<String> response = testRestTemplate.exchange(
                HOST + port + "/user/set-pet?userId=" + user.getId() + "&petId=" + pet.getId(),
                HttpMethod.PUT,
                null,
                String.class
        );

//        Then
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testFindRole() throws VolunteerAlreadyAppointedException {
//        Given
        User user = userService.save(USER_1);
        List<User> excepted = userService.findByRole(user.getRole());
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {};
//        When
        ResponseEntity<List<User>> response = testRestTemplate.exchange(
                HOST + port + "/user/find-by-role?role=" + user.getRole(),
                HttpMethod.GET,
                null,
                responseType
        );
//        Then
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testWhenUserNotFound() {
        User user = new User(444L, 1L, Role.USER, "22", "TEST", "TEST");
        Pet pet = petService.savePet(PET_1);
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
//        When
        ResponseEntity<User> editeResponse = testRestTemplate.exchange(
                HOST + port + "/user/" + user.getId(),
                HttpMethod.PUT,
                requestEntity,
                User.class
        );

        ResponseEntity<User> getResponse = testRestTemplate.getForEntity(
                HOST + port + "/user/" + user.getId(),
                User.class
        );

        ResponseEntity<User> deleteResponse = testRestTemplate.exchange(
                HOST + port + "/user/" + user.getId(),
                HttpMethod.DELETE,
                null,
                User.class
        );

        ResponseEntity<String> changeRoleResponse = testRestTemplate.exchange(
                HOST + port + "/change-role?id=" + user.getId() + "&role=" + Role.PET_OWNER,
                HttpMethod.PUT,
                null,
                String.class
        );

        ResponseEntity<String> setPetResponse = testRestTemplate.exchange(
                HOST + port + "/user/set-pet?userId=" + user.getId() + "&petId=" + pet.getId(),
                HttpMethod.PUT,
                null,
                String.class
        );


//        Then
        assertEquals(HttpStatus.NOT_FOUND, editeResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, changeRoleResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, setPetResponse.getStatusCode());}
}