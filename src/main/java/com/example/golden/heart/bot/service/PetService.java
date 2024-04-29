package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

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
}
