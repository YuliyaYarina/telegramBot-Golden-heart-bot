package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetOwner;
import com.example.golden.heart.bot.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/petOwner")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    @PostMapping
    public PetOwner savePetOwner(@RequestBody PetOwner petOwner) {
        return petOwnerService.savePetOwner(petOwner);
    }

    @PutMapping
    public ResponseEntity<PetOwner> editePetOwner(@RequestBody PetOwner petOwner) {
        PetOwner foundPetOwner = petOwnerService.editePetOwner(petOwner);
        if (foundPetOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundPetOwner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetOwner> getPetOwner(@PathVariable Long id) {
        PetOwner petOwner = petOwnerService.getPetOwnerById(id);
        if (petOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petOwner);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PetOwner> removePetOwner(@PathVariable Long id) {
        PetOwner petOwner = petOwnerService.getPetOwnerById(id);
        if (petOwner != null) {
            petOwnerService.removePetOwnerById(id);
            return ResponseEntity.ok(petOwner);
        }
        return ResponseEntity.notFound().build();
    }

}
