package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.service.AnimalShelterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
class AnimalShelterControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    AnimalShelterController animalShelterController;

    @Autowired
    AnimalShelterService animalShelterService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(animalShelterController).isNotNull();
    }

    @Test
    public void testSaveAnimalShelter() {
//        When
        ResponseEntity<AnimalShelter> response = testRestTemplate.postForEntity(
                HOST + port + "/animalShelter",
                ANIMAL_SHELTER_1,
                AnimalShelter.class
        );

        AnimalShelter excepted = ANIMAL_SHELTER_1;
        excepted.setId(response.getBody().getId());
//    Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testEditeAnimalShelter() {
//        Given
        AnimalShelter animalShelter = animalShelterService.saveAnimalShelter(ANIMAL_SHELTER_1);
        AnimalShelter editeShelter = new AnimalShelter(animalShelter.getId(), "EDITED", "Edited", "Edited");
        HttpEntity<AnimalShelter> requestEntity = new HttpEntity<>(editeShelter);

//        When
        ResponseEntity<AnimalShelter> response = testRestTemplate.exchange(
                HOST + port + "/animalShelter/" + animalShelter.getId(),
                HttpMethod.PUT,
                requestEntity,
                AnimalShelter.class

        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editeShelter, response.getBody());
    }

    @Test
    public void testGetAnimalShelter() {
//        Given
        AnimalShelter excepted = animalShelterService.saveAnimalShelter(ANIMAL_SHELTER_1);

//        When
        ResponseEntity<AnimalShelter> response = testRestTemplate.getForEntity(
                HOST + port + "/animalShelter/" + excepted.getId(),
                AnimalShelter.class
        );

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeleteAnimalShelter() {
//        Given
        AnimalShelter excepted = animalShelterService.saveAnimalShelter(ANIMAL_SHELTER_1);

//        When
        ResponseEntity<AnimalShelter> response = testRestTemplate.exchange(
                HOST + port + "/animalShelter/" + excepted.getId(),
                HttpMethod.DELETE,
                null,
                AnimalShelter.class
        );

        AnimalShelter afterDelete = animalShelterService.getAnimalShelterById(excepted.getId());

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

        assertNull(afterDelete);
    }

    @Test
    public void testWhenAnimalShelterNotFound() {
        AnimalShelter animalShelter = new AnimalShelter(444L, "TEST", "TEST", "TEST");
        HttpEntity<AnimalShelter> requestEntity = new HttpEntity<>(animalShelter);
//        When
        ResponseEntity<AnimalShelter> editeResponse = testRestTemplate.exchange(
                HOST + port + "/animalShelter/" + animalShelter.getId(),
                HttpMethod.PUT,
                requestEntity,
                AnimalShelter.class
        );

        ResponseEntity<AnimalShelter> getResponse = testRestTemplate.getForEntity(
                HOST + port + "/animalShelter/" + animalShelter.getId(),
                AnimalShelter.class
        );

        ResponseEntity<AnimalShelter> deleteResponse = testRestTemplate.exchange(
                HOST + port + "/animalShelter/" + animalShelter.getId(),
                HttpMethod.DELETE,
                null,
                AnimalShelter.class
        );

//        Then
        assertEquals(HttpStatus.NOT_FOUND, editeResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    @Test
    public void testSaveGetDeleteAddressScheme() {
        AnimalShelter animalShelter = animalShelterService.saveAnimalShelter(ANIMAL_SHELTER_WITH_PHOTO);
        Long id = animalShelter.getId();
        testSaveAddressSchema(id);
        testDownloadAddressSchema(id);
        testDeleteAddressSchema(id);

    }


    private void testSaveAddressSchema(Long animalShelterId) {
//        Подготовка тела запроса
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ClassPathResource("test-photo/1.png"));

//        Подготовка заголовка
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        When
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                HOST + port + "/animalShelter/" + animalShelterId + "/address/schema/post",
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDownloadAddressSchema(Long animalShelterId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                HOST + port + "/animalShelter/" + animalShelterId + "/photo",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDeleteAddressSchema(Long id) {
        ResponseEntity<String> response = testRestTemplate.exchange(
                HOST + port + "/animalShelter/" + id + "/photo",
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}