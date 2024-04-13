package com.example.golden.heart.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.Objects;

@Entity
public class PetOwner {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int phone;
    @OneToMany(mappedBy = "petOwner")
    private Collection<Pet> pets;

    public PetOwner() {
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetOwner petOwner = (PetOwner) o;
        return id == petOwner.id && Objects.equals(name, petOwner.name) && Objects.equals(phone, petOwner.phone) && Objects.equals(pets, petOwner.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, pets);
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pets=" + pets +
                '}';
    }
}
