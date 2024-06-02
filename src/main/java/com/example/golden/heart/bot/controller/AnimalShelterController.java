package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.service.AnimalShelterService;
import com.example.golden.heart.bot.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/animalShelter")
public class AnimalShelterController {
    @Autowired
    private AnimalShelterService animalShelterService;

    @Autowired
    private PhotoService photoService;

    @Operation(
            summary = "Добавить приют",
            tags = "Приют",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавьте приют"
            )
    )
    @PostMapping
    public ResponseEntity<AnimalShelter> createAnimalShelter(@RequestBody AnimalShelter animalShelter) {
        return ResponseEntity.ok(animalShelterService.saveAnimalShelter(animalShelter));
    }

    @Operation(
            summary = "Изменить информацию о приюте",
            tags = "Приют",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Введите информацию которую нужно изменить"
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<AnimalShelter> editeAnimalShelter(@RequestParam(name = "id приюта") Long id,
                                                            @RequestBody AnimalShelter animalShelter) {
        AnimalShelter foundAnimalShelter = animalShelterService.editAnimalShelter(id, animalShelter);
        if (foundAnimalShelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundAnimalShelter);
    }

    @Operation(
            summary = "Показать информацию о приюте",
            tags = "Приют"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AnimalShelter> getAnimalShelter(@RequestParam(name = "id приюта") Long id) {
        AnimalShelter animalShelter = animalShelterService.getAnimalShelterById(id);
        if (animalShelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalShelter);
    }

    @Operation(
            summary = "Удалить приют",
            tags = "Приют"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalShelter> removeAnimalShelter(@RequestParam(name = "id приюта") Long id) {
        AnimalShelter animalShelter = animalShelterService.getAnimalShelterById(id);
        if (animalShelter != null) {
            animalShelterService.removeAnimalShelterById(id);
            return ResponseEntity.ok(animalShelter);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Добавить фото проезда к приюту",
            tags = "Приют"
    )
    @PostMapping(value = "/{animalShelterId}/address/schema/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveAddressSchema(@PathVariable Long animalShelterId,
                                                    @Parameter(description = "Прикрепите фото")
                                                    @RequestParam MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 5000) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        animalShelterService.saveAddressPhoto(animalShelterId, file);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Показать фото проезда к приюту",
            tags = "Приют"
    )
    @GetMapping(value = "/{animalShelterId}/photo")
    public ResponseEntity<String> downloadPhoto(@PathVariable Long animalShelterId,
                              HttpServletResponse response) throws IOException {
        if (animalShelterService.getAnimalShelterById(animalShelterId) == null) {
            ResponseEntity.notFound().build();
        }
        if (photoService.findPhotoByAnimalShelterId(animalShelterId) == null) {
            return ResponseEntity.notFound().build();
        }
       animalShelterService.getPhoto(animalShelterId, response);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить фото проезда",
            tags = "Приют"
    )
    @DeleteMapping(value = "/{animalShelterId}/photo")
    public ResponseEntity<String> removePhoto(@PathVariable Long animalShelterId) {
        animalShelterService.removePhoto(animalShelterId);
        return ResponseEntity.ok().build();
    }
}
