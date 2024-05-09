package com.example.golden.heart.bot.command.commands.start.report;

import com.example.golden.heart.bot.command.enums.ReportState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хронения статусов отправки отчета
 * Все измения в статусах пользователя происходят в класса ReportCommand
 */
@Component
public class ReportStateStorage {
    private Map<Long, ReportState> reportStateMap = new HashMap<>();

    public Map<Long, ReportState> getReportStateMap() {
        return reportStateMap;
    }

    public ReportState getValue(Long chatId) {
        return reportStateMap.get(chatId);
    }

    public void replaceValue(Long chatId, ReportState reportState) {
        reportStateMap.replace(chatId, reportState);
    }

    public void remove(Long chatId) {
        reportStateMap.remove(chatId);
    }

    public void setValue(Long chatId, ReportState reportState) {
        reportStateMap.put(chatId, reportState);
    }

}
