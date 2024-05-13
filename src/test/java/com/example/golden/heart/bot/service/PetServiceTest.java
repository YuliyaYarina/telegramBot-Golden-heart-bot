package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.golden.heart.bot.constants.Constants.PET_1;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    @Test
    void savePet() {

        Pet pet = new Pet();

        when(petRepository.save(pet)).thenReturn(pet);

        Pet petSave = petService.savePet(pet);
        assertEquals(pet, petSave);

        verify(petRepository).save(pet);
    }

    @Test
    void editPet() {

        Long id = 1L;
        Pet existingPet = PET_1;

        Pet updatedPet = new Pet(existingPet.getId(), "Edited");

        when(petRepository.findById(id)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);

        Pet result = petService.editPet(id, updatedPet);

        assertNotNull(result);
        assertEquals("Кузя", result.getNick());

        verify(petRepository).save(any(Pet.class));
    }

    @ParameterizedTest
    @MethodSource("providePetForTesting")
    void getById(Long id, Pet expectedPet) {

        when(petRepository.findById(id)).thenReturn(Optional.ofNullable(expectedPet));

        Pet actualPet = petService.getPetById(id);

        assertEquals(expectedPet, actualPet, "Возвращаемое значение не равно ожидаемому");

        verify(petRepository).findById(id);
    }

    static Stream<Arguments> providePetForTesting() {
        Pet existingPet = new Pet(1L, "Барсик");
        return Stream.of(
                Arguments.of(1L, existingPet),
                Arguments.of(2L, null)
        );
    }

    @Test
    void removePetById() {
        Long petId = 1L;

        assertDoesNotThrow(() -> petService.removePetById(petId));

        verify(petRepository, times(1)).deleteById(petId);
    }

    @Test
    void saveAll() {

        Pet pet1 = new Pet(1L, "Барсик");
        Pet pet2 = new Pet(2L, "Кузя");
        List<Pet> pets = Arrays.asList(pet1, pet2);

        when(petRepository.saveAll(pets)).thenReturn(pets);


        List<Pet> savedPets = petService.saveAll(pets);

        assertEquals(pets, savedPets);
        verify(petRepository).saveAll(pets);

    }
}