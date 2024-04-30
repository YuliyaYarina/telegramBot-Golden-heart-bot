package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PetServiceTest {

   @Mock
   PetRepository petRepository;
   @InjectMocks
   PetService petService;

    @Test
    void savePet() {
        Pet pet = new Pet();
        pet.setNick("Бобби");
        Mockito.when(petRepository.save(pet)).thenReturn(pet);

        Pet savePet = petService.savePet(pet);

        Assertions.assertEquals(pet, savePet);
    }

    @Test
    void editePet() {
        Pet pet = new Pet();
        pet.setNick("Бобби");
        Mockito.when(petRepository.save(pet)).thenReturn(pet);

        Pet editePet = petService.editePet(pet);

        Assertions.assertEquals(pet, editePet);
    }

    @Test
    void getPetById() {
        Pet pet = new Pet();
        pet.setNick("Бобби");
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet petById = petService.getPetById(1L);

        Assertions.assertEquals(pet.getNick(), petById.getNick());
    }

    @Test
    void removePetById() {

    }

    @Test
    void saveAll() {
        Pet pet = new Pet();
        pet.setNick("Бобби");
        pet.setId(1L);

        Pet pet1 = new Pet();
        pet.setNick("Алекс");
        pet.setId(2L);

        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        pets.add(pet1);

        Mockito.when(petRepository.saveAll(pets)).thenReturn(pets);

        List<Pet> saveAll = petService.saveAll(pets);
        Assertions.assertEquals(pets, saveAll);
    }
}