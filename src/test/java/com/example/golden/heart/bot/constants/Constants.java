package com.example.golden.heart.bot.constants;

import com.example.golden.heart.bot.model.*;
import com.example.golden.heart.bot.model.enums.Role;

import java.nio.file.Path;
import java.time.LocalDate;

public class Constants {
    public static final String HOST = "http://localhost:";

    public static AnimalShelter ANIMAL_SHELTER_1 = new AnimalShelter(0L, "Test", "Test", "Shelter-1");
    public static AnimalShelter ANIMAL_SHELTER_WITH_PHOTO = new AnimalShelter(0L, "TEST", "PHOTO", "WITH");

    public static AnimalShelter EDITED_ANIMAL_SHELTER = new AnimalShelter(1L, "EDITED", "Edited", "Edited");

    public static Pet PET_1 = new Pet(0L, "Test");
    public static Pet PET_WITH_PHOTO = new Pet(0L, "Test photo");

    public static PetReport PET_REPORT_1 = new PetReport(0L, "Test", "Test", "Test", true, LocalDate.now());
    public static PetReport PET_REPORT_WITH_PHOTO = new PetReport(0L, "Test", "With", "Photo", true, LocalDate.now());

    public static User USER_1 = new User(0L, 1111L, Role.USER, "111", "Test", "Test");
    public static User VOLUNTEER = new User(0L, 1111L, Role.VOLUNTEER, "111", "Test", "Test");

    public static Photo PHOTO_1 = new Photo(0L, "Test", 111, "Test");

    public static DogBehaviorist DOG_BEHAVIORIST_1 = new DogBehaviorist(0L, "Test", 111);

    public static Path TEST_PATH = Path.of("TEST/PATH.jpg");


}
