package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalShelterServiceTest {

    @Mock
    AnimalShelterRepository animalShelterRepository;

    @InjectMocks
    AnimalShelterService animalShelterService;

    @Mock
    PetService petService;

    @Mock
    PhotoService photoService;

    @Test
    void saveAnimalShelter() {
        AnimalShelter animalShelter = new AnimalShelter();

        when(animalShelterRepository.save(animalShelter)).thenReturn(animalShelter);

        AnimalShelter saveAnimalShelter = animalShelterService.saveAnimalShelter(animalShelter);
        assertEquals(animalShelter, saveAnimalShelter);

        verify(animalShelterRepository).save(animalShelter);
    }

    @Test
    void editAnimalShelter() {

        Long id = 1L;
        AnimalShelter existingShelter = new AnimalShelter();
        existingShelter.setName("Старый приют");
        Photo existingPhoto = new Photo();
        existingPhoto.setFilePath("oldPhotoUrl");
        existingShelter.setAddressPhoto(existingPhoto);

        AnimalShelter updatedShelter = new AnimalShelter();
        updatedShelter.setName("Новый приют");
        Photo updatedPhoto = new Photo();
        updatedPhoto.setFilePath("newPhotoUrl");
        updatedShelter.setAddressPhoto(updatedPhoto);
        updatedShelter.setShelterPets(new HashSet<>());
        updatedShelter.setAddress("Новый адрес");
        updatedShelter.setWorkSchedule("Новое расписание");

        when(animalShelterRepository.findById(id)).thenReturn(Optional.of(existingShelter));
        when(animalShelterRepository.save(any(AnimalShelter.class))).thenReturn(updatedShelter);

        AnimalShelter result = animalShelterService.editAnimalShelter(id, updatedShelter);

        assertNotNull(result);
        assertEquals("Новый приют", result.getName());
        assertEquals(updatedPhoto, result.getAddressPhoto());
        verify(animalShelterRepository).save(any(AnimalShelter.class));

    }

    @ParameterizedTest
    @MethodSource("provideIdsForTesting")
    void getAnimalShelterById(Long id, AnimalShelter expected) {
        when(animalShelterRepository.findById(id)).thenReturn(Optional.ofNullable(expected));
        AnimalShelter actual = animalShelterService.getAnimalShelterById(id);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideIdsForTesting() {
        AnimalShelter existingShelter = new AnimalShelter();
        existingShelter.setId(1L);
        existingShelter.setName("Старый приют");
        Photo photo = new Photo();
        photo.setFilePath("oldPhotoUrl");
        existingShelter.setAddressPhoto(photo);

        return Stream.of(
                Arguments.of(1L, existingShelter),
                Arguments.of(2L, null)
        );
    }

    @Test
    void removeAnimalShelterById() {
        Long id = 1L;
        AnimalShelter animalShelter = mock(AnimalShelter.class);
        Photo mockPhoto = mock(Photo.class);
        List<Pet> mockPets = new ArrayList<>();

        when(animalShelterRepository.findById(id)).thenReturn(Optional.of(animalShelter));
        when(animalShelter.getShelterPets()).thenReturn(mockPets);
        when(animalShelter.getAddressPhoto()).thenReturn(mockPhoto);
        when(mockPhoto.getId()).thenReturn(2L);

        animalShelterService.removeAnimalShelterById(id);

        verify(petService).saveAll(mockPets);
        verify(photoService).removePhoto(any());
        verify(animalShelterRepository).deleteById(id);
    }

    @Test
    void saveAddressPhoto() {

    }

    @Test
    void getPhoto() {
    }

    @Test
    void removePhoto() {
    }
}