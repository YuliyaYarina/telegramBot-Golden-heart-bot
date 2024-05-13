package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.repository.PetRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class PetService {


    @Value("${pet.photo.dir.path}")
    private String petPhotoDir;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(PhotoService.class);

    /**
     * Сохраняет питомца
     * @param pet - сохраняемый питомец
     * @return соханненый питомц
     */
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * Редактирует информацию о питомце
     * @param id - id питомца
     * @param pet - изменненый питомец
     * @return измененый питомец
     */
    public Pet editPet(Long id, Pet pet) {
        return petRepository.findById(id)
                .map(foundPet -> {
                    foundPet.setNick(pet.getNick());
                    foundPet.setPhoto(pet.getPhoto());
                    foundPet.setAnimalShelter(pet.getAnimalShelter());
                    foundPet.setOwner(pet.getOwner());
                    return petRepository.save(foundPet);
                }).orElse(null);
    }

    /**
     * Находит питомца в БД по id
     * @param id - id питомца
     * @return найденый питомец или NULL
     */
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет питомца в БД по id
     * @param id - id питомца
     */
    public void removePetById(Long id) throws VolunteerAlreadyAppointedException {
        Pet pet = getPetById(id);
        User user = pet.getOwner();
        user.setRole(Role.USER);
        user.setPet(null);
        userService.save(user);
        petRepository.deleteById(id);
    }

    /**
     * Сохраняет всех преданных питомцев в БД
     * @param pets - питомцы которых нужно сохранить в БД
     * @return сохранненные питомцы
     */
    public List<Pet> saveAll(List<Pet> pets) {
        return petRepository.saveAll(pets);
    }

    /**
     * Сохраняет фото на диск и данные фото в базу данных
     * @param petId id питомца
     * @param file фото которую нужно сохранить
     * @return Фотография, которая была сохранена в базе данных.
     * @throws IOException может выбросить исключение
     */
    public Photo savePetPhoto(Long petId, MultipartFile file) throws IOException {
        Path path = photoService.uploadPhoto(petId, petPhotoDir, file);
        return savePhotoToDateBase(petId, path, file);
    }

    /**
     * Возвращает фото питомца
     * @param petId id питомца
     * @param response тело ответа
     * @throws IOException возможная ошибка
     */
    public void getPhoto(Long petId, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findPhotoByPetId(petId);
        photoService.getPhoto(photo, response);
    }

    /**
     *Удаляет фото схемы проезда и разрывет связ на стороне приюта
     * @param petId id питомца
     */
    public void removePhoto(Long petId) {
        Pet pet = getPetById(petId);
        Photo photo = photoService.findPhotoByPetId(petId);

        pet.setPhoto(null);
        savePet(pet);

        photoService.removePhoto(photo);
    }

    /**
     * Сохраняет информацию о фото в БД
     * @param petId id питомца
     * @param path путь к сохранненому фото
     * @param file само фото
     * @return сохранненая информация о фото
     */
    private Photo savePhotoToDateBase(Long petId, Path path, MultipartFile file) {
        Pet pet = getPetById(petId);
        if (pet == null) {
            logger.info("pet is null");
            return null;
        }
        Photo photo = photoService.findPhotoByPetId(petId);
        photo.setPet(pet);
        photo.setFilePath(path.toString());
        photo.setFileSize(file.getSize());
        photo.setMediaType(file.getContentType());

        pet.setPhoto(photo);

        photoService.savePhoto(photo);
        savePet(pet);

        return photo;
    }
}
