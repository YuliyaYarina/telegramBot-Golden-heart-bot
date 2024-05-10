package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.service.ReceivingUnprocessedReportsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("unprocessedReports")
public class ReceivingUnprocessedReportsController {

    private Logger logger = LoggerFactory.getLogger(ReceivingUnprocessedReportsController.class);

    @Autowired
    private ReceivingUnprocessedReportsService ruReportService;

    /**
     * реализация:
     * получения отчетов по id,
     * пометки, что отчет просмотренн,
     * возможности, выгружать отчет по его id
     */

    @GetMapping("/{id}")   // получение отчетов по id не помечая их как просмотренные
    public ResponseEntity<ResponseEntity<Object>> getPetReport(@PathVariable Long id) {
        return ResponseEntity.ok(ruReportService.getPetReport(id));
    }

    @GetMapping("/petReport/isViewed=false")  // получение списока не просмотренных отчетов
    public List<PetReport>  getAllPetReports(){
        return ruReportService.getAllPetReports();
    }

    @PutMapping("/{id}/viewed")     // помечает как просмотренный отчет
    public ResponseEntity<ResponseEntity<PetReport>> markReportAsViewed(@PathVariable Long id){
        return ResponseEntity.ok(ruReportService.markReportAsViewed(id));
    }

    @PostMapping("/{id}")   // отправляет сообщение по id отчета что отчет заполнен плохо
    public ResponseEntity<ResponseEntity<PetReport>> messageReport(@PathVariable Long id) {
        logger.info(" отправка сообщения пользователю по id:" + id);
        return ResponseEntity.ok(ruReportService.messageReport(id));
    }

}


