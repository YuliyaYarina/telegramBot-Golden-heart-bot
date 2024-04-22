package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.repository.OwnerReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetReportService {

    @Autowired
    private OwnerReportRepository ownerReportRepo;

    public PetReport saveOwnerReport(PetReport petReport) {
        return ownerReportRepo.save(petReport);
    }

    public PetReport editeOwnerReport(PetReport petReport) {
        return ownerReportRepo.save(petReport);
    }

    public PetReport getOwnerReportById(Long id) {
        return ownerReportRepo.findById(id).get();
    }

    public void removeOwnerReportById(Long id) {
        ownerReportRepo.deleteById(id);
    }
}
