package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo editPhoto(Long id, Photo photo) {
        return photoRepository.findById(id)
                .map(foundPhoto -> {
                    foundPhoto.setData(photo.getData());
                    foundPhoto.setPet(photo.getPet());
                    foundPhoto.setAnimalShelter(photo.getAnimalShelter());
                    foundPhoto.setFilePath(photo.getFilePath());
                    foundPhoto.setMediaType(photo.getMediaType());
                    foundPhoto.setPetReport(photo.getPetReport());
                    foundPhoto.setFileSize(photo.getFileSize());
                    return photoRepository.save(foundPhoto);
                }).orElse(null);
    }

    public Photo getPhoto(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    public void removePhoto(Long id) {
        photoRepository.deleteById(id);
    }

}
