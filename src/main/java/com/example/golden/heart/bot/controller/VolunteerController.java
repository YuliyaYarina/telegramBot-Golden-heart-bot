package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.Volunteer;
import com.example.golden.heart.bot.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<Volunteer> saveVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.saveVolunteer(volunteer);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Volunteer> editeVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer foundVolunteer = volunteerService.editeVolunteer(volunteer);
        if (foundVolunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.getVolunteer(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Volunteer> removeVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.getVolunteer(id);
        if (volunteer != null) {
            volunteerService.removeVolunteer(id);
            return ResponseEntity.ok(volunteer);
        }
        return ResponseEntity.notFound().build();
    }
}
