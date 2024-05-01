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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    @Mock
    PetReportRepository petReportRepository;

    @InjectMocks
    PetReportService petReportService;

    @Test
    void savePetReport() {
        PetReport petReport = new PetReport();

        when(petReportRepository.save(petReport)).thenReturn(petReport);

        PetReport petReportSave = petReportService.savePetReport(petReport);
        assertEquals(petReport, petReportSave);

        verify(petReportRepository).save(petReport);
    }

    @ParameterizedTest
    @MethodSource("provideIdsForPetReportTesting")
    void getPetReportByIdTest(Long id, PetReport expected) {

        when(petReportRepository.findById(id)).thenReturn(Optional.ofNullable(expected));
        PetReport actual = petReportService.getPetReportById(id);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideIdsForPetReportTesting() {
        PetReport existingPetReport = new PetReport(1L, "Сбалансированный", "Хорошее", "Нет изменений");
        PetReport newPetReport = new PetReport(2L, "Диета с высоким содержанием белка", "Отличное", "Более активный");
        return Stream.of(
                Arguments.of(1L, existingPetReport),
                Arguments.of(2L, newPetReport),
                Arguments.of(3L, null)
        );
    }

    @Test
    void removePetReportById() {
        Long petReportId = 1L;

        assertDoesNotThrow(() -> petReportService.removePetReportById(petReportId));

        verify(petReportRepository, times(1)).deleteById(petReportId);
    }

    @Test
    void editPetReport() {
        Long id = 1L;
        Pet pet = new Pet(1L, "Барсик");
        Collection<Photo> photos = new ArrayList<>();
        PetReport updatedPetReport = new PetReport(id, "Новая диета", "Улучшенное", "Изменение поведения");
        updatedPetReport.setPet(pet);
        updatedPetReport.setPhotos(photos);

        PetReport foundPetReport = new PetReport(id, "Старая диета", "Хорошее", "Без изменений");
        foundPetReport.setPet(pet);
        foundPetReport.setPhotos(photos);

        when(petReportRepository.findById(id)).thenReturn(Optional.of(foundPetReport));
        when(petReportRepository.save(any(PetReport.class))).thenReturn(updatedPetReport);

        PetReport result = petReportService.editPetReport(id, updatedPetReport);

        assertNotNull(result);
        assertEquals(updatedPetReport.getDiet(), result.getDiet());
        assertEquals(updatedPetReport.getWellBeing(), result.getWellBeing());
        assertEquals(updatedPetReport.getBehaviourChange(), result.getBehaviourChange());
        assertEquals(updatedPetReport.getPhotos(), result.getPhotos());
        assertEquals(updatedPetReport.getPet(), result.getPet());

        verify(petReportRepository).save(any(PetReport.class));

    }

    @Test
    void saveReportPhoto() {
    }

    @Test
    void getPhoto() {
    }

    @Test
    void removePhoto() {
    }
}