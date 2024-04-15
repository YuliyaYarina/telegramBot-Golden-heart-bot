package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.OwnerReport;
import com.example.golden.heart.bot.repository.OwnerReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerReportService {

    @Autowired
    private OwnerReportRepository ownerReportRepo;

    public OwnerReport saveOwnerReport(OwnerReport ownerReport) {
        return ownerReportRepo.save(ownerReport);
    }

    public OwnerReport editeOwnerReport(OwnerReport ownerReport) {
        return ownerReportRepo.save(ownerReport);
    }

    public OwnerReport getOwnerReportById(Long id) {
        return ownerReportRepo.findById(id).get();
    }

    public void removeOwnerReportById(Long id) {
        ownerReportRepo.deleteById(id);
    }
}
