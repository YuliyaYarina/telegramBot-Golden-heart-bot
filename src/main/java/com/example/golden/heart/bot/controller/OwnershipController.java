package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OwnershipController {
    @Autowired
    private OwnershipService ownershipService;

    @GetMapping
    public ResponseEntity<List<User>> findAllOwnersWithEndedProbation() {
        List<User> owners = ownershipService.findAllOwnersWithEndedProbation();
        if (owners.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owners);
    }

    @PutMapping("increase-probation")
    public ResponseEntity<String> increaseProbationPeriod(Long petId, Increase increase) {
        try {
            ownershipService.increaseProbationPeriod(petId, increase);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Испытательный срок увеличен");
    }

    @DeleteMapping
    public ResponseEntity<String> revokeOwnership(Long petId) {
        try {
            ownershipService.revokeOwnership(petId);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Отмена испытательного срока, питомца требуется вернуть в приют");
    }

    @PutMapping
    public ResponseEntity<String> confirmOwnership(Long petId) {
        try {
            ownershipService.confirmOwnership(petId);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Испытательный срок пройден успешно");
    }
}
