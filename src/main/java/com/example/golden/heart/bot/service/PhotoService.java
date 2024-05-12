package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    Logger logger = LoggerFactory.getLogger(PhotoService.class);

    /**
     * Сохраняет фото к диску.
     * @param id id сущности к которому привязан photo. (petReport or pet or animalShelter)
     * @param dir путь к файлу куда нужно сохранить фото
     * @param file фото которую нужно сохранить
     * @return путь к файлу где сохранено фото.
     * @throws IOException может выбросить ошибку
     */
    public Path uploadPhoto(Long id, String dir, MultipartFile file) throws IOException {
        logger.info("Wos invoked method for upload avatar");
        Path filePath = Path.of(dir, id + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    public void getPhoto(Photo photo, HttpServletResponse response) throws IOException {
        logger.info("Wos invoked method for get photo");

        Path path = Path.of(photo.getFilePath());

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            response.setStatus(200);
            response.setContentType(photo.getMediaType());
            response.setContentLength((int) photo.getFileSize());
            bis.transferTo(bos);
        }
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }
    public Photo editPhoto(Long id, Photo photo) {
        return photoRepository.findById(id)
                .map(foundPhoto -> {
                    foundPhoto.setPet(photo.getPet());
                    foundPhoto.setAnimalShelter(photo.getAnimalShelter());
                    foundPhoto.setFilePath(photo.getFilePath());
                    foundPhoto.setMediaType(photo.getMediaType());
                    foundPhoto.setPetReport(photo.getPetReport());
                    foundPhoto.setFileSize(photo.getFileSize());
                    return photoRepository.save(foundPhoto);
                }).orElse(null);
    }

    public void removePhoto(Photo photo) {
        logger.info("Wos invoked methods for remove photo");

        Path path = Path.of(photo.getFilePath());
        try {
            Files.delete(path);
        } catch (IOException ignored) {

        }
        photoRepository.deleteById(photo.getId());
    }

    public Photo getPhoto(Long id) {
        return photoRepository.findById(id).orElse(null);
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Photo findPhotoByReportId(Long petReportId) {
        return photoRepository.findByPetReportId(petReportId).orElse(new Photo());
    }

    public Photo findPhotoByAnimalShelterId(Long animalShelterId) {
        return photoRepository.findByAnimalShelterId(animalShelterId).orElse(new Photo());
    }

    public Photo findPhotoByPetId(Long petId) {
        return photoRepository.findByPetId(petId).orElse(new Photo());
    }
}
