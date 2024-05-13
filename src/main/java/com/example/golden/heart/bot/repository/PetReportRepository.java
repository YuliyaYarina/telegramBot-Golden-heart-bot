package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {
    List<PetReport> findAllByDateAndPet(LocalDate date, Pet pet);

    List<PetReport> findByIsViewed (boolean viewed);


    Optional<PetReport> findByPetIdAndDate(Long petId, LocalDate date);
    List<PetReport> findAllByPetId (Long petId);

    void deleteAllByPetId(Long petId);
}
