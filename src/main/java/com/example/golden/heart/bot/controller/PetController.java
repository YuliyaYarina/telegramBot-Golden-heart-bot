package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.service.PetService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public Pet savePet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    @PutMapping
    public ResponseEntity<Pet> editePet(@RequestBody Pet pet) {
        Pet foundPet = petService.editePet(pet);
        if (pet == null) {
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

    @PostMapping(value = "/{petId}/photo/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePetPhoto(@PathVariable Long petId,
                                               @RequestParam MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 500) {
            return ResponseEntity.ok("File is too big");
        }
        petService.savePetPhoto(petId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{petId}/photo")
    public void downloadPhoto(@PathVariable Long petId,
                              HttpServletResponse response) throws IOException {
        petService.getPhoto(petId, response);
    }

    @DeleteMapping(value = "/{petId}/photo")
    public ResponseEntity<String> removePhoto(@PathVariable Long petId) {
        petService.removePhoto(petId);
        return ResponseEntity.ok().build();
    }
}
