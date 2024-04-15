package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int phone;
    @OneToOne(mappedBy = "petOwner")
    @JsonIgnore
    private Pet pet;

    public PetOwner() {
    }

    public PetOwner(String name, int phone, Pet pet) {
        this.name = name;
        this.phone = phone;
        this.pet = pet;
    }
}
