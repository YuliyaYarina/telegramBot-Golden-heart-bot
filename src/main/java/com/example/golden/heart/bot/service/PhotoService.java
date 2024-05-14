package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PhotoRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    TelegramBot telegramBot;

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

    /**
     * Скачивает из базы Телеграмма отправленный пользователем фото,
     * и сохраняет его на диск
     * @param reportId id отчета к каторому нужно привязать фото
     * @param dir директория куда нужно сохранить фото
     * @param file файл для сохронения
     * @return путь где был сохранен фото
     * @throws IOException может выбросить ошибку
     */
    public Path downloadPhoto ( Long reportId, String dir, File file) throws IOException {
        logger.info("Wos invoked method for upload avatar from telegram");
        String fileUrl = telegramBot.getFullFilePath(file);

        Path filePtah = Path.of(dir, reportId + "." + getExtension(Objects.requireNonNull(file.filePath())));
        Files.createDirectories(filePtah.getParent());
        Files.deleteIfExists(filePtah);

        // Download the photo
        try {
            URL url = new URL(fileUrl);
            InputStream is = url.openStream();
            OutputStream out = Files.newOutputStream(filePtah, CREATE_NEW);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            is.close();
            out.close();

            System.out.println("Photo downloaded successfully: photo.jpg");
        } catch (IOException e) {
            System.out.println("Error downloading photo: " + e.getMessage());
        }
        return filePtah;
    }





    /**
     * Возвращает фото с диска
     * @param photo информация о фото
     * @param response тело ответа
     * @throws IOException может выбросить ошибку
     */
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

    /**
     * Сохраняет инофрмацию о фото в БД
     * @param photo информация о фото
     * @return сохранненая информация о фото
     */
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

    /**
     * Удаляет фото с диска и из БД
     * @param photo информация о фото
     */
    public void removePhoto(Photo photo) {
        logger.info("Wos invoked methods for remove photo");

        Path path = Path.of(photo.getFilePath());
        try {
            Files.delete(path);
        } catch (IOException ignored) {

        }
        photoRepository.deleteById(photo.getId());
    }

    /**
     *
     * @param id
     * @return
     */
    public Photo getPhoto(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param fileName  полное имя фото
     * @return тип файла
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Находит Photo по id отчету
     * @param petReportId id отчета
     * @return найденные Photo
     */
    public Photo findPhotoByReportId(Long petReportId) {
        return photoRepository.findByPetReportId(petReportId).orElse(new Photo());
    }

    /**
     * Находит Photo по id приюта
     * @param animalShelterId id приюта
     * @return возвращает найденные Photo
     */
    public Photo findPhotoByAnimalShelterId(Long animalShelterId) {
        return photoRepository.findByAnimalShelterId(animalShelterId).orElse(new Photo());
    }

    /**
     * Находит Photo по id питомца
     * @param petId id питомца
     * @return возвращает найденные Photo
     */
    public Photo findPhotoByPetId(Long petId) {
        return photoRepository.findByPetId(petId).orElse(new Photo());
    }
}
