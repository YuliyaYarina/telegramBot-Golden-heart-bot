package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class PetReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String diet;
    private String wellBeing;
    private String behaviourChange;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    @OneToMany(mappedBy = "petReport")
    @JsonIgnore
    private Collection<Photo> photos;

    public PetReport() {
    }

    /**
     * Конструктор для создания тестовых объектов
     */
    public PetReport(long id, String diet, String wellBeing, String behaviourChange) {
        this.id = id;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.behaviourChange = behaviourChange;
    }
}



