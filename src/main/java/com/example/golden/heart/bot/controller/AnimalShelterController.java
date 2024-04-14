package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.service.AnimalShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animalShelter")
public class AnimalShelterController {
    @Autowired
    private AnimalShelterService animalShelterService;

    @PostMapping
    public ResponseEntity<AnimalShelter> createAnimalShelter(@RequestBody AnimalShelter animalShelter) {
        return ResponseEntity.ok(animalShelterService.saveAnimalShelter(animalShelter));
    }

    @PutMapping
    public ResponseEntity<AnimalShelter> editeAnimalShelter(@RequestBody AnimalShelter animalShelter) {
        AnimalShelter foundAnimalShelter = animalShelterService.editeAnimalShelter(animalShelter);
        if (animalShelter == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundAnimalShelter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalShelter> getAnimalShelter(@PathVariable Long id) {
        AnimalShelter animalShelter = animalShelterService.getAnimalShelterById(id);
        if (animalShelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalShelter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalShelter> removeAnimalShelter(@PathVariable Long id) {
        AnimalShelter animalShelter = animalShelterService.getAnimalShelterById(id);
        if (animalShelter != null) {
            animalShelterService.removeAnimalShelterById(id);
            return ResponseEntity.ok(animalShelter);
        }
        return ResponseEntity.notFound().build();
    }

}
