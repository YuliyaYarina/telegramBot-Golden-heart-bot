package com.example.golden.heart.bot.service;


import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
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
class PhotoServiceTest {

    @Mock
    PhotoRepository photoRepository;
    @InjectMocks
    PhotoService photoService;

    @Test
    void savePhoto() {
        Photo photo = new Photo();
        photo.setId(1L);
        Mockito.when(photoRepository.save(photo)).thenReturn(photo);

        Photo savePhoto = photoService.savePhoto(photo);

        Assertions.assertEquals(photo, savePhoto);
    }

    @Test
    void editePhoto() {
        Photo photo = new Photo();
        photo.setId(1L);
        Mockito.when(photoRepository.save(photo)).thenReturn(photo);

        Photo editePhoto = photoService.editePhoto(photo);

        Assertions.assertEquals(photo, editePhoto);

    }

    @Test
    void getPhoto() {
        Photo photo = new Photo();
        photo.setId(1L);
        Mockito.when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));

        Photo photoById = photoService.getPhoto(1L);

        Assertions.assertEquals(photo.getId(), photoById.getId());
    }

    @Test
    void removePhoto() {
    }
}