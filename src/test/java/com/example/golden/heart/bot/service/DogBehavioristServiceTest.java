package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.repository.DogBehavioristRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogBehavioristServiceTest {

    @Mock
    DogBehavioristRepository dogBehavioristRepository;

    @InjectMocks
    DogBehavioristService dogBehavioristService;

    @Test
    void save() {
        DogBehaviorist dogBehaviorist = new DogBehaviorist();

        when(dogBehavioristRepository.save(dogBehaviorist)).thenReturn(dogBehaviorist);

        DogBehaviorist saveDogBehaviorist = dogBehavioristService.save(dogBehaviorist);
        assertEquals(dogBehaviorist, saveDogBehaviorist);

        verify(dogBehavioristRepository).save(dogBehaviorist);
    }

    @Test
    void edite() {
        Long id = 1L;
        DogBehaviorist existingDogBehaviorist = new DogBehaviorist();
        existingDogBehaviorist.setName("Test1");
        existingDogBehaviorist.setPhone(123456789);

        DogBehaviorist updatedDogBehaviorist = new DogBehaviorist();
        updatedDogBehaviorist.setName("Test2");
        updatedDogBehaviorist.setPhone(987654321);

        when(dogBehavioristRepository.findById(id)).thenReturn(Optional.of(existingDogBehaviorist));
        when(dogBehavioristRepository.save(ArgumentMatchers.any(DogBehaviorist.class))).thenReturn(updatedDogBehaviorist);

        DogBehaviorist result = dogBehavioristService.edite(id, updatedDogBehaviorist);

        assertNotNull(result);
        assertEquals("Test2", result.getName());
        assertEquals(987654321, result.getPhone());
        verify(dogBehavioristRepository).save(org.mockito.ArgumentMatchers.any(DogBehaviorist.class));
    }

    @ParameterizedTest
    @MethodSource("provideIdsForTesting")
    void getById(Long id, DogBehaviorist expected) {
        when(dogBehavioristRepository.findById(id)).thenReturn(Optional.ofNullable(expected));
        DogBehaviorist actual = dogBehavioristService.getById(id);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideIdsForTesting() {
        DogBehaviorist existingBehaviorist = new DogBehaviorist();
        existingBehaviorist.setId(1L);
        existingBehaviorist.setName("Тест");

        return Stream.of(
                Arguments.of(1L, existingBehaviorist),
                Arguments.of(2L, null)
        );
    }

    @Test
    void remove() {
        Long id = 1L;
        doNothing().when(dogBehavioristRepository).deleteById(id);
        boolean result = dogBehavioristService.remove(id);
        assertTrue(result);
        verify(dogBehavioristRepository, times(1)).deleteById(id);
    }

    @Test
    void findAll() {
        DogBehaviorist behaviorist1 = new DogBehaviorist();
        behaviorist1.setId(1L);
        behaviorist1.setName("Тест1");

        DogBehaviorist behaviorist2 = new DogBehaviorist();
        behaviorist2.setId(2L);
        behaviorist2.setName("Тест2");

        List<DogBehaviorist> expectedBehaviorists = Arrays.asList(behaviorist1, behaviorist2);
        when(dogBehavioristRepository.findAll()).thenReturn(expectedBehaviorists);

        List<DogBehaviorist> actualBehaviorists = dogBehavioristService.findAll();

        assertEquals(expectedBehaviorists, actualBehaviorists);
        verify(dogBehavioristRepository, times(1)).findAll();
    }
}