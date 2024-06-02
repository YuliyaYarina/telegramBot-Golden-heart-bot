package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.service.PetReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/petReport")
public class PetReportController {

    @Autowired
    private PetReportService petReportService;

    @Operation(
            summary = "Создать отчет",
            tags = "Отчет"
    )
    @PostMapping
    public ResponseEntity<PetReport> savePetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.savePetReport(petReport));
    }

    @Operation(
            summary = "Изменить отчет",
            tags = "Отчет"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PetReport> editePetReport(@PathVariable Long id, @RequestBody PetReport petReport) {
        PetReport foundReport = petReportService.editPetReport(id, petReport);
        if (foundReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundReport);
    }

    @Operation(
            summary = "Показать отчет",
            tags = "Отчет"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PetReport> getPetReport(@PathVariable(name = "id отчета") Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petReport);
    }

    @Operation(
            summary = "Показать все отчеты",
            tags = "Отчет"
    )
    @GetMapping("get-all-pet-reports")
    public ResponseEntity<List<PetReport>>  getAllPetReports(){
        List<PetReport> reports = petReportService.getAllPetReports();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    @Operation(
            summary = "Показать все отчеты по питомцу",
            tags = "Отчет"
    )
    @GetMapping("all-reports-for-pet")
    public ResponseEntity<List<PetReport>> findAllByPetId (@RequestParam(name = "id питомца") Long petId){
        List<PetReport> petReports = petReportService.findAllByPetId(petId);
        if (petReports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petReports);
    }

    @Operation(
            summary = "Удалить отчет",
            tags = "Отчет"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<PetReport> removePetReport(@PathVariable(name = "id отчета") Long id) {
        PetReport petReport = petReportService.getPetReportById(id);
        if (petReport != null) {
            petReportService.removePetReportById(id);
            return ResponseEntity.ok(petReport);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Загрузить фото в отчет",
            tags = "Отчет"
    )
    @PostMapping(value = "/{reportId}/photo/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveReportPhoto(@PathVariable(name = "id отчета") Long reportId,
                                                  @Parameter(description = "Добавьте фото")
                                                  @RequestParam MultipartFile photoReport) throws IOException {
        if (photoReport.getSize() > 2048 * 2048) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        petReportService.saveReportPhoto(reportId, photoReport);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Показать фото в отчете",
            tags = "Отчет"
    )
    @GetMapping(value = "/{petReportId}/photo")
    public void downloadPhoto(@PathVariable(name = "id отчета") Long petReportId,
                              HttpServletResponse response) throws IOException {
        petReportService.getPhoto(petReportId, response);
    }

    @Operation(
            summary = "Удалить фото в отчете",
            tags = "Отчет"
    )
    @DeleteMapping(value = "/{petReportId}/photo")
    public ResponseEntity<String> removePhoto(@PathVariable(name = "id отчета") Long petReportId) {
        petReportService.removePhoto(petReportId);
        return ResponseEntity.ok().build();
    }
}
