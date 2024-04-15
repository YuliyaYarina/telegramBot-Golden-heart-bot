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
        photoService.removePhoto(animalShelter.getPhoto().getId());
        animalShelterRepo.deleteById(id);
    }
}
