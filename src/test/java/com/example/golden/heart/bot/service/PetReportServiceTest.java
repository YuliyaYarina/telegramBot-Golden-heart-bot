package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PetReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.golden.heart.bot.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    @Mock
    PetReportRepository petReportRepository;


    @Mock
    PhotoService photoService;


    @InjectMocks
    PetReportService petReportService;

    @Test
    void savePetReport() {
//        Given
        when(petReportRepository.save(any())).thenReturn(PET_REPORT_1);
//      when
        PetReport actual = petReportService.savePetReport(PET_REPORT_1);
//        Then
        assertEquals(PET_REPORT_1, actual);

        verify(petReportRepository).save(any());
    }

    @Test
    void getPetReportByIdTest() {
//        Given
        when(petReportRepository.findById(anyLong())).thenReturn(Optional.ofNullable(PET_REPORT_1));
//        When
        PetReport actual = petReportService.getPetReportById(PET_REPORT_1.getId());
//        Then
        assertEquals(PET_REPORT_1, actual);
    }

    @Test
    void removePetReportById() {

        assertDoesNotThrow(() -> petReportService.removePetReportById(anyLong()));

        verify(petReportRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void editPetReport() {
        PetReport excepted = new PetReport(PET_REPORT_1.getId(), "Edited", "Edited", "Edited", true);

        when(petReportRepository.findById(anyLong())).thenReturn(Optional.of(PET_REPORT_1));
        when(petReportRepository.save(any())).thenReturn(excepted);

        PetReport result = petReportService.editPetReport(PET_REPORT_1.getId(), excepted);

        assertNotNull(result);
        assertEquals(excepted, result);

        verify(petReportRepository).save(any());
    }

}