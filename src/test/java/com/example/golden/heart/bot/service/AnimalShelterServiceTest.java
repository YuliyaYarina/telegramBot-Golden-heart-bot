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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static com.example.golden.heart.bot.constants.Constants.*;
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

//        Given
        when(animalShelterRepository.save(any())).thenReturn(ANIMAL_SHELTER_1);

//        Given
        AnimalShelter actual = animalShelterService.saveAnimalShelter(ANIMAL_SHELTER_1);
        assertEquals(ANIMAL_SHELTER_1, actual);

//        Then
        verify(animalShelterRepository).save(any());
        assertEquals(ANIMAL_SHELTER_1, actual);

    }

    @Test
    void editAnimalShelter() {
//      Given
        when(animalShelterRepository.findById(anyLong())).thenReturn(Optional.of(ANIMAL_SHELTER_1));
        when(animalShelterRepository.save(any())).thenReturn(EDITED_ANIMAL_SHELTER);

//        When
        AnimalShelter actual = animalShelterService.editAnimalShelter(1L, EDITED_ANIMAL_SHELTER);

//        Then
        assertNotNull(actual);

        verify(animalShelterRepository).save(any());
        assertEquals(EDITED_ANIMAL_SHELTER, actual);
    }

    @Test
    void getAnimalShelterById() {
//        Given
        when(animalShelterRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ANIMAL_SHELTER_1));
//        When
        AnimalShelter actual = animalShelterService.getAnimalShelterById(ANIMAL_SHELTER_1.getId());

        assertEquals(ANIMAL_SHELTER_1, actual);
    }

    @Test
    void removeAnimalShelterById() {
//      Given
        List<Pet> pets = new ArrayList<>();
        pets.add(PET_1);
        AnimalShelter animalShelter = ANIMAL_SHELTER_1;
        animalShelter.setShelterPets(pets);
        when(animalShelterRepository.findById(anyLong())).thenReturn(Optional.of(ANIMAL_SHELTER_1));

//        When
        animalShelterService.removeAnimalShelterById(ANIMAL_SHELTER_1.getId());

//        Then
        verify(petService).saveAll(any());
        if (ANIMAL_SHELTER_1.getAddressPhoto() != null) {
            verify(photoService).removePhoto(any());
        }

        if (!ANIMAL_SHELTER_1.getShelterPets().isEmpty()) {
            verify(petService).saveAll(any());
        }
        verify(animalShelterRepository).deleteById(ANIMAL_SHELTER_1.getId());
    }

}