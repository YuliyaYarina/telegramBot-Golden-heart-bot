package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.service.DogBehavioristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dagBehaviorist")
public class DogBehavioristController {

    @Autowired
    private DogBehavioristService dogBehavioristService;

    @Operation(
            summary = "Добавить кинолога",
            tags = "Кинолог"
    )
    @PostMapping
    public ResponseEntity<DogBehaviorist> createDogBehaviorist(@Parameter(description = "Добавьте информацию")
            @RequestBody DogBehaviorist dogBehaviorist) {
        return ResponseEntity.ok(dogBehavioristService.save(dogBehaviorist));
    }

    @Operation(
            summary = "Изменить информацию о кинологе",
            tags = "Кинолог"
    )
    @PutMapping("/{id}")
    public ResponseEntity<DogBehaviorist> editeDogBehaviorist(@PathVariable(name = "id кинолога") Long id,
                                                              @Parameter(description = "Ведите новую информацию")
                                                              @RequestBody DogBehaviorist dogBehaviorist) {
        DogBehaviorist editedDogBehaviorist = dogBehavioristService.edite(id, dogBehaviorist);
        if (editedDogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedDogBehaviorist);
    }

    @Operation(
            summary = "Показать информацию о кинологе",
            tags = "Кинолог"
    )
    @GetMapping("/{id}")
    public ResponseEntity<DogBehaviorist> getDogBehaviorist(@PathVariable(name = "id кинолога") Long id) {
        DogBehaviorist dogBehaviorist = dogBehavioristService.getById(id);
        if (dogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogBehaviorist);
    }

    @Operation(
            summary = "Удалить кинолога",
            tags = "Кинолог"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DogBehaviorist> removeDogBehaviorist(@PathVariable(name = "id кинолога") Long id) {
        DogBehaviorist dogBehaviorist = dogBehavioristService.getById(id);
        if (dogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }
        dogBehavioristService.remove(id);
        return ResponseEntity.ok(dogBehaviorist);
    }
}
