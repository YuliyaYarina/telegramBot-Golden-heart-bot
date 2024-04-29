package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    @OneToOne
    @JoinColumn(name = "animal_shelter_id")
    @JsonIgnore
    private AnimalShelter animalShelter;

    @ManyToOne
    @JoinColumn(name = "pet_report_id")
    @JsonIgnore
    private PetReport petReport;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    public Photo() {

    }

}
