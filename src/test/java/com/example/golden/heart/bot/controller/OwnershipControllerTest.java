package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.service.OwnershipService;
import com.example.golden.heart.bot.service.PetService;
import com.example.golden.heart.bot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class OwnershipControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    OwnershipController ownershipController;

    @Autowired
    OwnershipService ownershipService;

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(ownershipController).isNotNull();
    }

    @Test
    public void testFindAllOwnersEndedProbation() throws VolunteerAlreadyAppointedException {
//        Given
        User owner = USER_1;
        owner.setProbationPeriod(0);
        User user = userService.save(owner);

        List<User> excepted = new ArrayList<>();
        excepted.add(user);

        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {};

//        Then
        ResponseEntity<List<User>> response = testRestTemplate.exchange(
                HOST + port + "/owner/findAllWithEndedProbation",
                HttpMethod.GET,
                null,
                responseType
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

}
