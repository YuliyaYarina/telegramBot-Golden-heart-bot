package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.service.PetService;
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
import static com.example.golden.heart.bot.constants.Constants.HOST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
class PetControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private PetController petController;

    @Autowired
    PetService petService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(petController).isNotNull();
    }

    @Test
    public void testSavePet() {
//        When
        ResponseEntity<Pet> response = testRestTemplate.postForEntity(
                HOST + port + "/pet",
                PET_1,
                Pet.class
        );

        Pet excepted = PET_1;
        excepted.setId(response.getBody().getId());
//    Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testEditePet() {
//        Given
        Pet pet = petService.savePet(PET_1);
        Pet editePet = new Pet(pet.getId(), "EDITED");
        HttpEntity<Pet> requestEntity = new HttpEntity<>(editePet);

//        When
        ResponseEntity<Pet> response = testRestTemplate.exchange(
                HOST + port + "/pet/" + pet.getId(),
                HttpMethod.PUT,
                requestEntity,
                Pet.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editePet, response.getBody());
    }

    @Test
    public void testGetPet() {
//        Given
        Pet excepted = petService.savePet(PET_1);

//        When
        ResponseEntity<Pet> response = testRestTemplate.getForEntity(
                HOST + port + "/pet/" + excepted.getId(),
                Pet.class
        );

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeletePet() {
//        Given
        Pet excepted = petService.savePet(PET_1);

//        When
        ResponseEntity<Pet> response = testRestTemplate.exchange(
                HOST + port + "/pet/" + excepted.getId(),
                HttpMethod.DELETE,
                null,
                Pet.class
        );

        Pet afterDelete = petService.getPetById(excepted.getId());

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

        assertNull(afterDelete);
    }

    @Test
    public void testWhenPetNotFound() {
        Pet pet = new Pet(444L, "TEST");
        HttpEntity<Pet> requestEntity = new HttpEntity<>(pet);
//        When
        ResponseEntity<Pet> editeResponse = testRestTemplate.exchange(
                HOST + port + "/pet/" + pet.getId(),
                HttpMethod.PUT,
                requestEntity,
                Pet.class
        );

        ResponseEntity<Pet> getResponse = testRestTemplate.getForEntity(
                HOST + port + "/pet/" + pet.getId(),
                Pet.class
        );

        ResponseEntity<Pet> deleteResponse = testRestTemplate.exchange(
                HOST + port + "/pet/" + pet.getId(),
                HttpMethod.DELETE,
                null,
                Pet.class
        );

//        Then
        assertEquals(HttpStatus.NOT_FOUND, editeResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    @Test
    public void testSaveGetDeletePhoto() {
        Pet pet = petService.savePet(PET_WITH_PHOTO);
        Long id = pet.getId();
        testSavePhoto(id);
        testDownloadPhoto(id);
        testDeletePhoto(id);

    }


    private void testSavePhoto(Long petId) {
//        Подготовка тела запроса
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ClassPathResource("test-photo/1.png"));

//        Подготовка заголовка
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        When
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                HOST + port + "/pet/" + petId + "/photo/post",
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDownloadPhoto(Long petId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                HOST + port + "/pet/" + petId + "/photo",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDeletePhoto(Long id) {
        ResponseEntity<String> response = testRestTemplate.exchange(
                HOST + port + "/pet/" + id + "/photo",
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}