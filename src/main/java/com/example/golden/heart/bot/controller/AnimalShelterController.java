package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.service.AnimalShelterService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/animalShelter")
public class AnimalShelterController {
    @Autowired
    private AnimalShelterService animalShelterService;

    @PostMapping
    public ResponseEntity<AnimalShelter> createAnimalShelter(@RequestBody AnimalShelter animalShelter) {
        return ResponseEntity.ok(animalShelterService.saveAnimalShelter(animalShelter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalShelter> editeAnimalShelter(@PathVariable Long id, @RequestBody AnimalShelter animalShelter) {
        AnimalShelter foundAnimalShelter = animalShelterService.editAnimalShelter(id, animalShelter);
        if (foundAnimalShelter == null) {
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

    @PostMapping(value = "/{animalShelterId}/address/schema/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveAddressSchema(@PathVariable Long animalShelterId,
                                                    @RequestParam MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        animalShelterService.saveAddressPhoto(animalShelterId, file);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/{animalShelterId}/photo")
    public void downloadPhoto(@PathVariable Long animalShelterId,
                              HttpServletResponse response) throws IOException {
        animalShelterService.getPhoto(animalShelterId, response);
    }

    @DeleteMapping(value = "/{animalShelterId}/photo")
    public ResponseEntity<String> removePhoto(@PathVariable Long animalShelterId) {
        animalShelterService.removePhoto(animalShelterId);
        return ResponseEntity.ok().build();
    }
}
