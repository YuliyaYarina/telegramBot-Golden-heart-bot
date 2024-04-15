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
    @Lob
    @JsonIgnore
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "animal_shelter_id")
    private AnimalShelter animalShelter;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private OwnerReport ownerReport;

    public Photo() {
    }

}
