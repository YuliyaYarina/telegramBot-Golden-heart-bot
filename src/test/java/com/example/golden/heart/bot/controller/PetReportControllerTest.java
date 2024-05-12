package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.service.PetReportService;
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
class PetReportControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    PetReportController petReportController;

    @Autowired
    PetReportService petReportService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(petReportController).isNotNull();
    }

    @Test
    public void testSavePetReport() {
//        When
        ResponseEntity<PetReport> response = testRestTemplate.postForEntity(
                HOST + port + "/petReport",
                PET_REPORT_1,
                PetReport.class
        );

        PetReport excepted = PET_REPORT_1;
        excepted.setId(response.getBody().getId());
//    Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());
    }

    @Test
    public void testEditePetReport() {
//        Given
        PetReport petReport = petReportService.savePetReport(PET_REPORT_1);
        PetReport editePetReport = new PetReport(petReport.getId(), "EDITED", "Edited", "Edited", true);
        HttpEntity<PetReport> requestEntity = new HttpEntity<>(editePetReport);

//        When
        ResponseEntity<PetReport> response = testRestTemplate.exchange(
                HOST + port + "/petReport/" + petReport.getId(),
                HttpMethod.PUT,
                requestEntity,
                PetReport.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(editePetReport, response.getBody());
    }

    @Test
    public void testGetPetReport() {
//        Given
        PetReport excepted = petReportService.savePetReport(PET_REPORT_1);

//        When
        ResponseEntity<PetReport> response = testRestTemplate.getForEntity(
                HOST + port + "/petReport/" + excepted.getId(),
                PetReport.class
        );

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

    }

    @Test
    public void testDeletePetReport() {
//        Given
        PetReport excepted = petReportService.savePetReport(PET_REPORT_1);

//        When
        ResponseEntity<PetReport> response = testRestTemplate.exchange(
                HOST + port + "/petReport/" + excepted.getId(),
                HttpMethod.DELETE,
                null,
                PetReport.class
        );

        PetReport afterDelete = petReportService.getPetReportById(excepted.getId());

//        Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(excepted, response.getBody());

        assertNull(afterDelete);
    }

    @Test
    public void testWhenPetReportNotFound() {
        PetReport petReport = new PetReport(444L, "TEST", "TEST", "TEST", true);
        HttpEntity<PetReport> requestEntity = new HttpEntity<>(petReport);
//        When
        ResponseEntity<PetReport> editeResponse = testRestTemplate.exchange(
                HOST + port + "/petReport/" + petReport.getId(),
                HttpMethod.PUT,
                requestEntity,
                PetReport.class
        );

        ResponseEntity<PetReport> getResponse = testRestTemplate.getForEntity(
                HOST + port + "/petReport/" + petReport.getId(),
                PetReport.class
        );

        ResponseEntity<PetReport> deleteResponse = testRestTemplate.exchange(
                HOST + port + "/petReport/" + petReport.getId(),
                HttpMethod.DELETE,
                null,
                PetReport.class
        );

//        Then
        assertEquals(HttpStatus.NOT_FOUND, editeResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    @Test
    public void testSaveGetDeleteReportPhoto() {
        PetReport petReport = petReportService.savePetReport(PET_REPORT_WITH_PHOTO);
        Long id = petReport.getId();
        testSaveReportPhoto(id);
        testDownloadReportPhoto(id);
        testDeleteReportPhoto(id);

    }


    private void testSaveReportPhoto(Long petReportId) {
//        Подготовка тела запроса
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("photoReport", new ClassPathResource("test-photo/1.png"));

//        Подготовка заголовка
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        When
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                HOST + port + "/petReport/" + petReportId + "/photo/post",
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDownloadReportPhoto(Long reportId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                HOST + port + "/petReport/" + reportId + "/photo",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void testDeleteReportPhoto(Long reportId) {
        ResponseEntity<String> response = testRestTemplate.exchange(
                HOST + port + "/petReport/" + reportId + "/photo",
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}