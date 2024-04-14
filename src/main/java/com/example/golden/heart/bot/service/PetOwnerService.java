package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetOwner;
import com.example.golden.heart.bot.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public PetOwner savePetOwner(PetOwner petOwner) {
        return petOwnerRepository.save(petOwner);
    }

    public PetOwner editePetOwner(PetOwner petOwner) {
        return petOwnerRepository.save(petOwner);
    }

    public PetOwner getPetOwnerById(Long id) {
        return petOwnerRepository.findById(id).get();
    }

    public void removePetOwnerById(Long id) {
        petOwnerRepository.deleteById(id);
    }
}
