package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
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
public class AnimalShelterService {
    @Value("${animal.shelter.address.photo.dir.path}")
    private String animalShelterDir;
    @Autowired
    private AnimalShelterRepository animalShelterRepo;
    @Autowired
    private PetService petService;
    @Autowired
    private PhotoService photoService;

    Logger logger = LoggerFactory.getLogger(AnimalShelterService.class);

    public AnimalShelter saveAnimalShelter(AnimalShelter animalShelter) {
        return animalShelterRepo.save(animalShelter);
    }

    public AnimalShelter editeAnimalShelter(AnimalShelter animalShelter) {
        return animalShelterRepo.save(animalShelter);
    }

    public AnimalShelter getAnimalShelterById(Long id) {
        return animalShelterRepo.findById(id).get();
    }

    /**
     * Удаляет AnimalShelter
     * Перед удалением AnimalShelter удаляет все фото схем
     * и Вызывает setAnimalShelter(null) ко всем pets прията
     * @param id AnimalShelter
     */
    public void removeAnimalShelterById(Long id) {
        AnimalShelter animalShelter = getAnimalShelterById(id);
        List<Pet> pets = animalShelter.getShelterPets().stream().toList();
        for (Pet pet : pets) {
            pet.setAnimalShelter(null);
        }
        petService.saveAll(pets);
        photoService.removePhoto(animalShelter.getAddressPhoto().getId());
        animalShelterRepo.deleteById(id);
    }

    public Photo saveAddressPhoto(Long animalShelterId, MultipartFile file) throws IOException {
        Path path = photoService.uploadPhoto(animalShelterId, animalShelterDir, file);
        return savePhotoToDateBase(animalShelterId, path, file);
    }

    public Photo savePhotoToDateBase(Long animalShelterId, Path filePath, MultipartFile file) {
        AnimalShelter animalShelter = getAnimalShelterById(animalShelterId);
        if (animalShelter == null) {
            logger.info("animalShelter is null");
            return null;
        }

        Photo photo = photoService.findPhotoByAnimalShelterId(animalShelterId);
        photo.setAnimalShelter(animalShelter);
        photo.setFilePath(filePath.toString());
        photo.setFileSize(file.getSize());
        photo.setMediaType(file.getContentType());

        animalShelter.setAddressPhoto(photo);
        saveAnimalShelter(animalShelter);

        return photoService.savePhoto(photo);
    }
}
