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

    public PetReport editOwnerReport(Long id, PetReport petReport) {
        return ownerReportRepo.findById(id)
                .map(foundReport -> {
                    foundReport.setPet(petReport.getPet());
                    foundReport.setDiet(petReport.getDiet());
                    foundReport.setWellBeing(petReport.getWellBeing());
                    foundReport.setBehaviourChange(petReport.getBehaviourChange());
                    foundReport.setPhotos(petReport.getPhotos());
                    return ownerReportRepo.save(foundReport);
                }).orElse(null);
    }

    public PetReport getOwnerReportById(Long id) {
        return ownerReportRepo.findById(id).orElse(null);
    }

    public void removeOwnerReportById(Long id) {
        ownerReportRepo.deleteById(id);
    }
}
