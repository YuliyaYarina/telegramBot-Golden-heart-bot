package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
