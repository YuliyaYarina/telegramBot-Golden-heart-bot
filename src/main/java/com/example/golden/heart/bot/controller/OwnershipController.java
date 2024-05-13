package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnershipController {
    @Autowired
    private OwnershipService ownershipService;

    @GetMapping("/findAllWithEndedProbation")
    public ResponseEntity<List<User>> findAllOwnersWithEndedProbation() {
        List<User> owners = ownershipService.findAllOwnersWithEndedProbation();
        if (owners.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owners);
    }

    @PutMapping("/increase-probation")
    public ResponseEntity<String> increaseProbationPeriod(Long petId, Increase increase) {
        try {
            ownershipService.increaseProbationPeriod(petId, increase);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (VolunteerAlreadyAppointedException e) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Испытательный срок увеличен");
    }

    @DeleteMapping("revokeOwnerShip")
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

    @PutMapping("confirmOwnership")
    public ResponseEntity<String> confirmOwnership(Long petId) {
        try {
            ownershipService.confirmOwnership(petId);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (VolunteerAlreadyAppointedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Испытательный срок пройден успешно");
    }
}
