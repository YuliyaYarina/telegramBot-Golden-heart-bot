package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bot_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chtId;
    @Enumerated(EnumType.STRING)
    private Role role;

    private int phone;
    private String name;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    public User() {
    }
}
