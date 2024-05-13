package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.service.DogBehavioristService;
import com.example.golden.heart.bot.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class DogBehavioristControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private DogBehavioristController dogBehavioristController;

    @Autowired
    DogBehavioristService dogBehavioristService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(dogBehavioristController).isNotNull();
    }

    @Test
    public void testSaveDogBehaviorist() {
//        When
        ResponseEntity<DogBehaviorist> response = testRestTemplate.postForEntity(
                HOST + port + "/dogBehaviorist",
                DOG_BEHAVIORIST_1,
                DogBehaviorist.class
        );

        DogBehaviorist excepted = DOG_BEHAVIORIST_1;
        excepted.setId(response.getBody().getId());
//    Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testEditeDogBehaviorist() {
//        Given
        DogBehaviorist dogBehaviorist = dogBehavioristService.save(DOG_BEHAVIORIST_1);
        DogBehaviorist editeDogBehaviorist = new DogBehaviorist(dogBehaviorist.getId(), "Edited", 111);
        HttpEntity<DogBehaviorist> requestEntity = new HttpEntity<>(editeDogBehaviorist);

//        When
        ResponseEntity<DogBehaviorist> response = testRestTemplate.exchange(
                HOST + port + "/dogBehaviorist/" + dogBehaviorist.getId(),
                HttpMethod.PUT,
                requestEntity,
                DogBehaviorist.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editeDogBehaviorist, response.getBody());
    }

    @Test
    public void testGetDogBehaviorist() {
//        Given
        DogBehaviorist excepted = dogBehavioristService.save(DOG_BEHAVIORIST_1);

//        When
        ResponseEntity<DogBehaviorist> response = testRestTemplate.getForEntity(
                HOST + port + "/dogBehaviorist/" + excepted.getId(),
                DogBehaviorist.class
        );

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeleteDogBehaviorist() {
//        Given
        DogBehaviorist excepted = dogBehavioristService.save(DOG_BEHAVIORIST_1);

//        When
        ResponseEntity<DogBehaviorist> response = testRestTemplate.exchange(
                HOST + port + "/dogBehaviorist/" + excepted.getId(),
                HttpMethod.DELETE,
                null,
                DogBehaviorist.class
        );

        DogBehaviorist afterDelete = dogBehavioristService.getById(excepted.getId());

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

        assertNull(afterDelete);
    }
}
