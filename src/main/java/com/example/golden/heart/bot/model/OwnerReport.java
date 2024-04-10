package com.example.golden.heart.bot.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class OwnerReport {
    @Id
    @GeneratedValue
    private long id;
    private String diet;
    private String wellBeing;
    private String behaviourChange;

    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }

    public String getBehaviourChange() {
        return behaviourChange;
    }

    public void setBehaviourChange(String behaviourChange) {
        this.behaviourChange = behaviourChange;
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
        OwnerReport that = (OwnerReport) o;
        return id == that.id && fileSize == that.fileSize && Objects.equals(diet, that.diet) && Objects.equals(wellBeing, that.wellBeing) && Objects.equals(behaviourChange, that.behaviourChange) && Objects.equals(filePath, that.filePath) && Objects.equals(mediaType, that.mediaType) && Arrays.equals(data, that.data) && Objects.equals(pet, that.pet);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, diet, wellBeing, behaviourChange, filePath, fileSize, mediaType, pet);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "OwnerReport{" +
                "id=" + id +
                ", diet='" + diet + '\'' +
                ", wellBeing='" + wellBeing + '\'' +
                ", behaviourChange='" + behaviourChange + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", pet=" + pet +
                '}';
    }
}
