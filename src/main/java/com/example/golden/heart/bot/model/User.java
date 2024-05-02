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
    private String userName;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    public User() {
    }
    public User(Long chtId, String name, String userName) {
        this.chtId = chtId;
        this.name = name;
        this.userName = userName;
        this.role = Role.USER;
    }

    /**
     * Конструктор для создания объектов для тестирования
     */
    public User(Long id, Long chtId, Role role, int phone, String name, String userName) {
        this.id = id;
        this.chtId = chtId;
        this.role = role;
        this.phone = phone;
        this.name = name;
        this.userName = userName;
    }
}
