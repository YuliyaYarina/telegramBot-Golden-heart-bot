package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalShelterServiceTest {

    @Mock
    AnimalShelterRepository animalShelterRepository;

    @InjectMocks
    AnimalShelterService animalShelterService;


    @Test
    void saveAnimalShelter() {
        AnimalShelter animalShelter = new AnimalShelter();
        Mockito.when(animalShelterRepository.save(animalShelter)).thenReturn(animalShelter);

        AnimalShelter saveAnimalShelter = animalShelterService.saveAnimalShelter(animalShelter);

        Assertions.assertEquals(animalShelter, saveAnimalShelter);
    }

    @Test
    void editeAnimalShelter() {
        AnimalShelter animalShelter = new AnimalShelter();
        Mockito.when(animalShelterRepository.save(animalShelter)).thenReturn(animalShelter);

        AnimalShelter editAnimalShelter = animalShelterService.editeAnimalShelter(animalShelter);

        Assertions.assertEquals(animalShelter, editAnimalShelter);
    }

    @Test
    void getAnimalShelterById() {
        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.setName("Доброе сердце");
        Mockito.when(animalShelterRepository.findById(1L)).thenReturn(Optional.of(animalShelter));

        AnimalShelter animalShelterById = animalShelterService.getAnimalShelterById(1L);

        Assertions.assertEquals(animalShelter.getName(), animalShelterById.getName());
    }

    @Test
    void removeAnimalShelterById() {
    }
}