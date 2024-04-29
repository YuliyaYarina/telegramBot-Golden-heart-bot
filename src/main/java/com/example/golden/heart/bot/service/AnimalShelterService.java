package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalShelterService {
    @Autowired
    private AnimalShelterRepository animalShelterRepo;
    @Autowired
    private PetService petService;
    @Autowired
    private PhotoService photoService;

    public AnimalShelter saveAnimalShelter(AnimalShelter animalShelter) {
        return animalShelterRepo.save(animalShelter);
    }

    public AnimalShelter editAnimalShelter(Long id, AnimalShelter animalShelter) {
        return animalShelterRepo.findById(id)
                .map(foundShelter -> {
                    foundShelter.setName(animalShelter.getName());
                    foundShelter.setShelterPets(animalShelter.getShelterPets());
                    foundShelter.setAddress(animalShelter.getAddress());
                    foundShelter.setWorkSchedule(animalShelter.getWorkSchedule());
                    foundShelter.setAddressPhoto(animalShelter.getAddressPhoto());
                    return animalShelterRepo.save(foundShelter);
                }).orElse(null);
    }

    public AnimalShelter getAnimalShelterById(Long id) {
        return animalShelterRepo.findById(id).orElse(null);
    }

    /**
     * Удаляет AnimalShelter
     * Перед удалением AnimalShelter удаляет все фото схем
     * и Вызывает setAnimalShelter(null) ко всем pets прията
     *
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
}
