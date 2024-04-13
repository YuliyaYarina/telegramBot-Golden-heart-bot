package com.example.golden.heart.bot.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class PetOwner {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String phone;
    @OneToOne(mappedBy = "petOwner")
    private Pet pet;

    public PetOwner(String name, String phone, Pet pet) {
        this.name = name;
        this.phone = phone;
        this.pet = pet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetOwner petOwner = (PetOwner) o;
        return id == petOwner.id && Objects.equals(name, petOwner.name) && Objects.equals(phone, petOwner.phone) && Objects.equals(pet, petOwner.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, pet);
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pet=" + pet +
                '}';
    }
}
