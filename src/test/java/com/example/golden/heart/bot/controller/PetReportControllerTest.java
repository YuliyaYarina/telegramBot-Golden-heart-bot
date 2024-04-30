package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.repository.PetReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetReportControllerTest {

    @Autowired
    PetReportRepository petReportRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void saveOwnerReport() {
    }

    @Test
    void editeOwnerReport() {
    }

    @Test
    void getOwnerReport() {
        PetReport petReport = new PetReport();
        petReport.setDiet("Умеренная");
        petReport.setWellBeing("Счастлив");
        petReport.setBehaviourChange("Несчастлив");
        PetReport save = petReportRepository.save(petReport);
        long id = save.getId();
        ResponseEntity<PetReport> forEntity = testRestTemplate.getForEntity("/petReport/" + id, PetReport.class);
        HttpStatusCode statusCode = forEntity.getStatusCode();
        Assertions.assertEquals(200, statusCode.value());
        assertNotNull(forEntity.getBody());
        Assertions.assertEquals(forEntity.getBody().getDiet() , petReport.getDiet());
        Assertions.assertEquals(forEntity.getBody().getWellBeing() , petReport.getWellBeing());
        Assertions.assertEquals(forEntity.getBody().getBehaviourChange() , petReport.getBehaviourChange());
    }

    @Test
    void removeOwnerReport() {
    }
}