package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PetReportRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class PetReportService {

    @Value("${pet.report.photo.dir.path}")
    private String petReportDir;

    @Autowired
    private PetReportRepository petReportRepo;

    @Autowired
    private PhotoService photoService;

    Logger logger = LoggerFactory.getLogger(PetReportService.class);

    public PetReport savePetReport(PetReport petReport) {
        return petReportRepo.save(petReport);
    }
    public PetReport getPetReportById(Long id) {
        return petReportRepo.findById(id).get();
    }

    public void removePetReportById(Long id) {
        petReportRepo.deleteById(id);
    }
    public PetReport getOwnerReportById(Long id) {
        return petReportRepo.findById(id).orElse(null);
    }

    public PetReport editPetReport(Long id, PetReport petReport) {
        return petReportRepo.findById(id)
                .map(foundReport -> {
                    foundReport.setPet(petReport.getPet());
                    foundReport.setDiet(petReport.getDiet());
                    foundReport.setWellBeing(petReport.getWellBeing());
                    foundReport.setBehaviourChange(petReport.getBehaviourChange());
                    foundReport.setPhotos(petReport.getPhotos());
                    return petReportRepo.save(foundReport);
                }).orElse(null);
    }

    /**
     * Сохраняет фото на диск и данные фото в базу данных
     * @param petReportId id отчета
     * @param file фото которую нужно сохранить
     * @return Фотография, которая была сохранена в базе данных.
     * @throws IOException может выбросить исключение
     */
    public Photo saveReportPhoto(Long petReportId, MultipartFile file) throws IOException {
        Path filePath = photoService.uploadPhoto(petReportId, petReportDir, file);
        return savePhotoToDateBase(petReportId, filePath, file);
    }

    public void getPhoto(Long petReportId, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findPhotoByReportId(petReportId);
        photoService.getPhoto(photo, response);
    }

    /**
     * Удаляет фото из базы и из диска
     * @param petReportId id petReport
     */

    public void removePhoto(Long petReportId) {
        Photo photo = photoService.findPhotoByReportId(petReportId);

        photoService.removePhoto(photo);
    }

    private Photo savePhotoToDateBase(Long petReportId, Path filePath, MultipartFile file) {
        PetReport petReport = getPetReportById(petReportId);
        if (petReport == null) {
            logger.info("petReport is null");
            return null;
        }

        Photo photo = photoService.findPhotoByReportId(petReportId);
        photo.setPetReport(petReport);
        photo.setFilePath(filePath.toString());
        photo.setFileSize(file.getSize());
        photo.setMediaType(file.getContentType());

        return photoService.savePhoto(photo);
    }

}
