package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.service.ReceivingUnprocessedReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            summary = "Получить отчет по id, не помечая как просмотренный"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntity<Object>> getPetReport(@PathVariable(required = false, name = "id отчета") Long id) {
        return ResponseEntity.ok(ruReportService.getPetReport(id));
    }

    @Operation(
            summary = "Список, не просмотренных отчетов"
    )
    @GetMapping("/petReport/isViewed=false")
    public List<PetReport>  getAllPetReports(){
        return ruReportService.getAllPetReports();
    }

    @Operation(
            summary = "Пометить, что отчет просмотрен"
    )
    @PutMapping("/{id}/viewed")
    public ResponseEntity<ResponseEntity<PetReport>> markReportAsViewed(@PathVariable(required = false, name = "id отчета") Long id){
        return ResponseEntity.ok(ruReportService.markReportAsViewed(id));
    }

    @Operation(
            summary = "Отправление сообщения, о плохо заполненном отчете"
    )
    @PostMapping("/{id}")
    public ResponseEntity<ResponseEntity<PetReport>> messageReport(@Parameter(description = "Отправить усыновителю: Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.")
                                                                       @PathVariable(required = false, name = "id отчета") Long id) {
        logger.info(" отправка сообщения пользователю по id:" + id);
        return ResponseEntity.ok(ruReportService.messageReport(id));
    }

}


