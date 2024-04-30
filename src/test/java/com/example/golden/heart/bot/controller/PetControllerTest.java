package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest {

    @Autowired
    PetRepository petRepository;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void savePet() {
    }

    @Test
    void editePet() {
    }

    @Test
    void getPet() {
        Pet pet = new Pet();
        pet.setNick("Барсик");
        Pet save = petRepository.save(pet);
        long id = save.getId();

        ResponseEntity<Pet> forEntity = testRestTemplate.getForEntity("/pet/" + id, Pet.class);
        HttpStatusCode statusCode = forEntity.getStatusCode();

        Assertions.assertEquals(200, statusCode.value());
        assertNotNull(forEntity.getBody());
        Assertions.assertEquals(forEntity.getBody().getNick(), pet.getNick());
    }

    @Test
    void removePet() {
    }
}