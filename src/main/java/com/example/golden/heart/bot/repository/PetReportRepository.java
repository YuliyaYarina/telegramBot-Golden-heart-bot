package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {
    List<PetReport> findAllByDateAndPet(LocalDate date, Pet pet);
}
