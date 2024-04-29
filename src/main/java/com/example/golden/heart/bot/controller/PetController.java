package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public Pet savePet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> editPet(@PathVariable Long id, @RequestBody Pet pet) {
        Pet foundPet = petService.editPet(id, pet);
        if (foundPet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> removePet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            petService.removePetById(id);
            return ResponseEntity.ok(pet);
        }
        return ResponseEntity.notFound().build();
    }
}
