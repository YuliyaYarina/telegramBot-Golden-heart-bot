package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.repository.AnimalShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalShelterService {
    @Autowired
    private AnimalShelterRepository animalShelterRepo;

    public AnimalShelter saveAnimalShelter(AnimalShelter animalShelter) {
        return animalShelterRepo.save(animalShelter);
    }

    public AnimalShelter editeAnimalShelter(AnimalShelter animalShelter) {
        return animalShelterRepo.save(animalShelter);
    }

    public AnimalShelter getAnimalShelterById(Long id) {
        return animalShelterRepo.findById(id).get();
    }

    public void removeAnimalShelterById(Long id) {
        animalShelterRepo.deleteById(id);
    }
}
