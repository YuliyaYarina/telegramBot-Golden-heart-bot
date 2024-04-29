package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.service.PetReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ownerReport")
public class PetReportController {

    @Autowired
    private PetReportService petReportService;

    @PostMapping
    public ResponseEntity<PetReport> savePetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.savePetReport(petReport));
    }

    @PostMapping(value = "/{reportId}/photo/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveReportPhoto(@PathVariable Long reportId,
                                             @RequestParam MultipartFile photoReport) throws IOException {
        if (photoReport.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        petReportService.saveReportPhoto(reportId   , photoReport);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<PetReport> editeOwnerReport(@RequestBody PetReport petReport) {
        PetReport foundReport = petReportService.editePetReport(petReport);
        if (foundReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetReport> getOwnerReport(@PathVariable Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetReport> removeOwnerReport(@PathVariable Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport != null) {
            petReportService.removePetReportById(id);
            return ResponseEntity.ok(petReport);
        }
        return ResponseEntity.notFound().build();
    }

}
