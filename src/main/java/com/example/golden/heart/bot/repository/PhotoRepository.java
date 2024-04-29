package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByPetReportId(Long petReportId);

    Optional<Photo> findByAnimalShelterId(Long animalShelterId);
}
