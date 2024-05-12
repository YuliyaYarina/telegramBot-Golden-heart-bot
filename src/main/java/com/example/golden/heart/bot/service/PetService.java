package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
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

    Logger logger = LoggerFactory.getLogger(PhotoService.class);

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

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

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public void removePetById(Long id) {
        petRepository.deleteById(id);
    }

    public List<Pet> saveAll(List<Pet> pets) {
        return petRepository.saveAll(pets);
    }

    /**
     * Сохраняет фото на диск и данные фото в базу данных
     * @param petId id животного
     * @param file фото которую нужно сохранить
     * @return Фотография, которая была сохранена в базе данных.
     * @throws IOException может выбросить исключение
     */
    public Photo savePetPhoto(Long petId, MultipartFile file) throws IOException {
        Path path = photoService.uploadPhoto(petId, petPhotoDir, file);
        return savePhotoToDateBase(petId, path, file);
    }

    public void getPhoto(Long petId, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findPhotoByPetId(petId);
        photoService.getPhoto(photo, response);
    }

    /**
     *Удаляет фото схемы проезда и разрывет связ на стороне приюта
     * @param petId id животного
     */
    public void removePhoto(Long petId) {
        Pet pet = getPetById(petId);
        Photo photo = photoService.findPhotoByPetId(petId);

        pet.setPhoto(null);
        savePet(pet);

        photoService.removePhoto(photo);
    }

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
