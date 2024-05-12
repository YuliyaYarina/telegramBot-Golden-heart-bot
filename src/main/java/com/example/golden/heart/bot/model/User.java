package com.example.golden.heart.bot.model;

import com.example.golden.heart.bot.model.enums.Role;
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

    private Long chatId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phone;
    private String name;
    private String userName;

    @JsonIgnore
    private String chosenPetType;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private Integer probationPeriod;

    public User(Long chatId, String phone) {
        this.chatId = chatId;
        this.phone = phone;
    }
    public User() {
    }
    public User(Long chatId, String name, String userName) {
        this.chatId = chatId;
        this.name = name;
        this.userName = userName;
        this.role = Role.USER;
    }

    /**
     * Конструктор для создания объектов для тестирования
     */
    public User(Long id, Long chatId, Role role, String phone, String name, String userName) {
        this.id = id;
        this.chatId = chatId;
        this.role = role;
        this.phone = phone;
        this.name = name;
        this.userName = userName;
    }
}
