package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
public class AnimalShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String workSchedule;

    private String name;

    @OneToMany(mappedBy = "animalShelter")
    @JsonIgnore
    private Collection<Pet> shelterPets;

    @OneToOne
    @JoinColumn(name = "address_photo_id")
    @JsonIgnore
    private Photo addressPhoto;


    public AnimalShelter() {
    }
}
