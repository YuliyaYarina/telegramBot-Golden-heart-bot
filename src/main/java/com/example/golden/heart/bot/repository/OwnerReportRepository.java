package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.PetReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerReportRepository extends JpaRepository<PetReport, Long> {
}
