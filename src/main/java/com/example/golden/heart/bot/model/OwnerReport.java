package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class OwnerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String diet;
    private String wellBeing;
    private String behaviourChange;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    @OneToMany(mappedBy = "ownerReport")
    @JsonIgnore
    private Collection<Photo> photos;

    public OwnerReport() {
    }

}



