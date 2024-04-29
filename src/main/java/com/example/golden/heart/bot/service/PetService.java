package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.PetRepository;
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

    public Pet editePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).get();
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
