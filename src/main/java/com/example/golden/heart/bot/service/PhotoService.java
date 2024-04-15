package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo editePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo getPhoto(Long id) {
        return photoRepository.findById(id).get();
    }

    public void removePhoto(Long id) {
        photoRepository.deleteById(id);
    }

}
