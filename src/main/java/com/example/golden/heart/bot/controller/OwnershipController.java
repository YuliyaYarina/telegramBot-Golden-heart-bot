package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.service.OwnershipService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ownership")
public class OwnershipController {

    @Autowired
    private OwnershipService ownershipService;

    @Operation(
            summary = "Получить список всех владельцев животных, с закончевшимся испытательным сроком",
            tags = "Испытательный срок"
    )
    @GetMapping("/findAllWithEndedProbation")
    public ResponseEntity<List<User>> findAllOwnersWithEndedProbation() {
        List<User> owners = ownershipService.findAllOwnersWithEndedProbation();
        if (owners.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owners);
    }

    @Operation(
            summary = "Увеличить испытательный срок",
            tags = "Испытательный срок"
    )
    @PutMapping("/increase-probation")
    public ResponseEntity<String> increaseProbationPeriod(@RequestParam(name = "id питомца")Long petId,
                                                          @RequestParam(name = "кол-во дней")Increase increase) {
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

    @Operation(
            summary = "Отметить, что испытательный срок провален",
            tags = "Испытательный срок"
    )
    @DeleteMapping("/revokeOwnerShip")
    public ResponseEntity<String> revokeOwnership(@RequestParam(name = "id питомца") Long petId) {
        try {
            ownershipService.revokeOwnership(petId);
        } catch (IllegalArgumentException | NullUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Отмена испытательного срока, питомца требуется вернуть в приют");
    }

    @Operation(
            summary = "Отметить, что испытательный срок успешно пройден",
            tags = "Испытательный срок"
    )
    @PutMapping("/confirmOwnership")
    public ResponseEntity<String> confirmOwnership(@RequestParam(name = "id питомца")Long petId) {
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
