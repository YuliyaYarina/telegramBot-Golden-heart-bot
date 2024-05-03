package com.example.golden.heart.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DogBehaviorist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int phone;

    public DogBehaviorist() {
    }

    public DogBehaviorist(Long id, String name, int phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
