package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    private boolean isViewed;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    @OneToMany(mappedBy = "petReport")
    @JsonIgnore
    private Collection<Photo> photos;

    private LocalDate date;

    public PetReport() {
    }

    /**
     * Конструктор для создания тестовых объектов
     */
    public PetReport(long id, String diet, String wellBeing, String behaviourChange, boolean isViewed) {
        this.id = id;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.behaviourChange = behaviourChange;
        this.isViewed = isViewed;
    }
}



