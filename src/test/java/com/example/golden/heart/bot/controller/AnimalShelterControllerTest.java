package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalShelterControllerTest {

    @Autowired
    AnimalShelterRepository animalShelterRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void createAnimalShelter() {
    }

    @Test
    void editeAnimalShelter() {
    }

    @Test
    void getAnimalShelter() {
        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.setName("Доброе сердце");
        animalShelter.setAddress("шоссе Чехова, 51");
        animalShelter.setWorkSchedule("10");
        AnimalShelter save = animalShelterRepository.save(animalShelter);
        long id = save.getId();

        ResponseEntity<AnimalShelter> forEntity = testRestTemplate.getForEntity("/animalShelter/" + id, AnimalShelter.class);
        HttpStatusCode statusCode = forEntity.getStatusCode();

        Assertions.assertEquals(200, statusCode.value());
        assertNotNull(forEntity.getBody());
        Assertions.assertEquals(forEntity.getBody().getName() , animalShelter.getName());
        Assertions.assertEquals(forEntity.getBody().getAddress() , animalShelter.getAddress());
        Assertions.assertEquals(forEntity.getBody().getWorkSchedule() , animalShelter.getWorkSchedule());
    }

    @Test
    void removeAnimalShelter() {

    }
}