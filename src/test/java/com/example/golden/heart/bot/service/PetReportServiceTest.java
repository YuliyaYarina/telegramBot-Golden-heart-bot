package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.repository.PetReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    @Mock
    PetReportRepository petReportRepository;
    @InjectMocks
    PetReportService petReportService;

    @Test
    void saveOwnerReport() {
        PetReport petReport = new PetReport();
        Mockito.when(petReportRepository.save(petReport)).thenReturn(petReport);

        PetReport saveOwnerReport = petReportService.savePetReport(petReport);

        Assertions.assertEquals(petReport, saveOwnerReport);
    }

    @Test
    void editeOwnerReport() {
        PetReport petReport = new PetReport();
        Mockito.when(petReportService.savePetReport(petReport)).thenReturn(petReport);

        PetReport editeOwnerReport = petReportService.editPetReport(1L, petReport);

        Assertions.assertEquals(petReport, editeOwnerReport);
    }

//    @Test
//    void getOwnerReportById() {
//        PetReport petReport = new PetReport();
//        petReport.setId(1L);
//        Mockito.when(petReportService.getPetReportById(1L)).thenReturn(petReport);
//
//        PetReport getOwnerReportById = petReportService.getPetReportById(1L);
//
//        Assertions.assertEquals(petReport, getOwnerReportById);
//    }

    @Test
    void removeOwnerReportById() {

    }
}