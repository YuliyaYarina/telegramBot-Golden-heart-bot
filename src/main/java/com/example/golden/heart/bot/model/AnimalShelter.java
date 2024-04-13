package com.example.golden.heart.bot.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class AnimalShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String address;
    private String workSchedule;
    private String filePath;
    private long  fileSize;
    private String mediaType;
    private byte[] data;

    @OneToMany(mappedBy = "animalShelter")
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Collection<Pet> getShelterPets() {
        return shelterPets;
    }

    public void setShelterPets(Collection<Pet> shelterPets) {
        this.shelterPets = shelterPets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalShelter that = (AnimalShelter) o;
        return id == that.id && fileSize == that.fileSize && Objects.equals(address, that.address) && Objects.equals(workSchedule, that.workSchedule) && Objects.equals(filePath, that.filePath) && Objects.equals(mediaType, that.mediaType) && Arrays.equals(data, that.data) && Objects.equals(shelterPets, that.shelterPets);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, address, workSchedule, filePath, fileSize, mediaType, shelterPets);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalShelter{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", workSchedule='" + workSchedule + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", shelterPets=" + shelterPets +
                '}';
    }
}
