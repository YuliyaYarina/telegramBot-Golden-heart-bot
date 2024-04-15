package com.example.golden.heart.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class AnimalShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String workSchedule;

    private String name;

    @OneToMany(mappedBy = "animalShelter")
    @JsonIgnore
    private Collection<Pet> shelterPets;

    public AnimalShelter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public Collection<Pet> getShelterPets() {
        return shelterPets;
    }

    public void setShelterPets(Collection<Pet> shelterPets) {
        this.shelterPets = shelterPets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalShelter that = (AnimalShelter) o;
        return id == that.id && Objects.equals(address, that.address) && Objects.equals(workSchedule, that.workSchedule) && Objects.equals(name, that.name) && Objects.equals(shelterPets, that.shelterPets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, workSchedule, name, shelterPets);
    }

    @Override
    public String toString() {
        return "AnimalShelter{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", workSchedule='" + workSchedule + '\'' +
                ", name='" + name + '\'' +
                ", shelterPets=" + shelterPets +
                '}';
    }
}
