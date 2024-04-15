package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.OwnerReport;
import com.example.golden.heart.bot.service.OwnerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ownerReport")
public class OwnerReportController {

    @Autowired
    private OwnerReportService ownerReportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveOwnerReport(@RequestBody OwnerReport ownerReport,
                                                       @RequestParam MultipartFile photoReport) {
        if (photoReport.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<OwnerReport> editeOwnerReport(@RequestBody OwnerReport ownerReport) {
        OwnerReport foundReport = ownerReportService.editeOwnerReport(ownerReport);
        if (foundReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerReport> getOwnerReport(@PathVariable Long id) {
        OwnerReport ownerReport = ownerReportService.getOwnerReportById(id);
        if (ownerReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OwnerReport> removeOwnerReport(@PathVariable Long id) {
        OwnerReport ownerReport = ownerReportService.getOwnerReportById(id);
        if (ownerReport != null) {
            ownerReportService.removeOwnerReportById(id);
            return ResponseEntity.ok(ownerReport);
        }
        return ResponseEntity.notFound().build();
    }

}
