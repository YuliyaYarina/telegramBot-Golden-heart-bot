package com.example.golden.heart.bot.constants;

import com.example.golden.heart.bot.model.AnimalShelter;
import com.example.golden.heart.bot.model.Pet;

public class Constants {
    public static final String HOST = "http://localhost:";

    public static AnimalShelter ANIMAL_SHELTER_1 = new AnimalShelter(0L, "Test", "Test", "Shelter-1");
    public static AnimalShelter ANIMAL_SHELTER_WITH_PHOTO = new AnimalShelter(0L, "TEST", "PHOTO", "WITH");

    public static Pet PET_1 = new Pet(0L, "Test");
    public static Pet PET_WITH_PHOTO = new Pet(0L, "Test photo");
}
