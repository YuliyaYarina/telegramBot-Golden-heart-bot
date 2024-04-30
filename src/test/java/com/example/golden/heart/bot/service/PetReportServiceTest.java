package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.repository.OwnerReportRepository;
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
    OwnerReportRepository ownerReportRepository;
    @InjectMocks
    PetReportService petReportService;

    @Test
    void saveOwnerReport() {
        PetReport petReport = new PetReport();
        Mockito.when(ownerReportRepository.save(petReport)).thenReturn(petReport);

        PetReport saveOwnerReport = petReportService.saveOwnerReport(petReport);

        Assertions.assertEquals(petReport, saveOwnerReport);
    }

    @Test
    void editeOwnerReport() {
        PetReport petReport = new PetReport();
        Mockito.when(ownerReportRepository.save(petReport)).thenReturn(petReport);

        PetReport editeOwnerReport = petReportService.editeOwnerReport(petReport);

        Assertions.assertEquals(petReport, editeOwnerReport);
    }

    @Test
    void getOwnerReportById() {
        PetReport petReport = new PetReport();
        petReport.setId(1L);
        Mockito.when(ownerReportRepository.findById(1L)).thenReturn(Optional.of(petReport));

        PetReport getOwnerReportById = petReportService.getOwnerReportById(1L);

        Assertions.assertEquals(petReport, getOwnerReportById);
    }

    @Test
    void removeOwnerReportById() {

    }
}