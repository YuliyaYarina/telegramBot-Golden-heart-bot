package com.example.golden.heart.bot.service;

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
import java.time.LocalDate;
import java.util.List;

@Service
public class PetReportService {

    @Value("${pet.report.photo.dir.path}")
    private String petReportDir;

    @Autowired
    private PetReportRepository petReportRepo;

    @Autowired
    private PhotoService photoService;

    Logger logger = LoggerFactory.getLogger(PetReportService.class);

    /**
     * Сохраняет отчет в БД
     * @param petReport - отчет
     * @return сохраненный отчет
     */
    public PetReport savePetReport(PetReport petReport) {
        return petReportRepo.save(petReport);
    }

    /**
     * Находит отчет в БД по id
     * @param id- id отчета
     * @return найденный отчет
     */
    public PetReport getPetReportById(Long id) {
        return petReportRepo.findById(id).orElse(null);
    }

    /**
     * Удаляет отчет по id
     * @param id - id отчета
     */
    public void removePetReportById(Long id) {
        petReportRepo.deleteById(id);
    }

    /**
     * Редактирует отчет
     * @param id - id отчета
     * @param petReport - изменненый отчет
     * @return измененный отчет
     */
    public PetReport editPetReport(Long id, PetReport petReport) {
        return petReportRepo.findById(id)
                .map(foundReport -> {
                    foundReport.setPet(petReport.getPet());
                    foundReport.setDiet(petReport.getDiet());
                    foundReport.setWellBeing(petReport.getWellBeing());
                    foundReport.setBehaviourChange(petReport.getBehaviourChange());
                    foundReport.setPhotos(petReport.getPhotos());
                    foundReport.setDate(petReport.getDate());
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

    /**
     * Ищет фото в БД
     * @param petReportId - id отчета
     * @param response - тело запроса
     * @throws IOException может выдать ошибку
     */
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

    /**
     * Ишет отчет по id животного и по дате если не  сохроняет новый отчет с id питомца и сегоднешной датой
     * @param petId id питомца
     * @param date дата для которого нужен отчет
     * @return найденный отчет или новый отчет если не найдено
     */
    public PetReport findByPetIdAndDate(Long petId, LocalDate date) {
        return petReportRepo.findByPetIdAndDate(petId, date).orElse(null);
    }

    /**
     * Сохраняет фото отчета
     * @param petReportId id отчета
     * @param filePath путь фаила, где гранится фото
     * @param file фото
     * @return сохранненая информация о фото
     */
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

    /**
     *
     * @return все отчеты
     */
    public List<PetReport> getAllPetReports() {
        return petReportRepo.findAll();
    }

    /**
     *
     * @param petId- id питомца
     * @return все отчеты у конкретного питомца
     */
    public List<PetReport> findAllByPetId(Long petId) {
        return petReportRepo.findAllByPetId(petId);
    }
}
