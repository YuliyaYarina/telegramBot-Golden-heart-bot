package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
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


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Photo findPhotoByReportId(Long petReportId) {
        return photoRepository.findByPetReportId(petReportId).orElse(new Photo());
    }

}
