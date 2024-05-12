package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.service.DogBehavioristService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dagBehaviorist")
public class DogBehavioristController {

    @Autowired
    private DogBehavioristService dogBehavioristService;

    @Operation(
            summary = "Добавить кинолога"
    )
    @PostMapping
    public ResponseEntity<DogBehaviorist> createDogBehaviorist(@RequestBody DogBehaviorist dogBehaviorist) {
        return ResponseEntity.ok(dogBehavioristService.save(dogBehaviorist));
    }

    @Operation(
            summary = "Изменить информацию о кинологе"
    )
    @PutMapping("/{id}")
    public ResponseEntity<DogBehaviorist> editeDogBehaviorist(@PathVariable Long id,
                                                              @RequestBody DogBehaviorist dogBehaviorist) {
        DogBehaviorist editedDogBehaviorist = dogBehavioristService.edite(id, dogBehaviorist);
        if (editedDogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editedDogBehaviorist);
    }

    @Operation(
            summary = "Показать информацию о кинологе"
    )
    @GetMapping("/{id}")
    public ResponseEntity<DogBehaviorist> getDogBehaviorist(@PathVariable Long id) {
        DogBehaviorist dogBehaviorist = dogBehavioristService.getById(id);
        if (dogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogBehaviorist);
    }

    @Operation(
            summary = "Удалить кинолога"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DogBehaviorist> removeDogBehaviorist(@PathVariable Long id) {
        DogBehaviorist dogBehaviorist = dogBehavioristService.getById(id);
        if (dogBehaviorist == null) {
            return ResponseEntity.notFound().build();
        }
        dogBehavioristService.remove(id);
        return ResponseEntity.ok(dogBehaviorist);
    }
}
