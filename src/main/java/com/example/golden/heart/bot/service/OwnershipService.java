package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.exceptions.NullUserException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Increase;
import com.example.golden.heart.bot.model.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OwnershipService {
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;
    @Autowired
    TelegramBotSender telegramBotSender;

    private final String NO_SUCH_PET = "Питомец с таким id не найден";
    private final String NO_OWNER = "У питомца нет владельца";

    public List<User> findAllOwnersWithEndedProbation() {
        return userService.findByProbationPeriod(0);
    }

    public void increaseProbationPeriod(Long petId, Increase increase) {
        Pet pet = petService.getPetById(petId);
        checkPet(pet);
        User owner = pet.getOwner();
        checkOwner(owner);
        if (owner.getProbationPeriod() == null) {
            owner.setProbationPeriod(0);
        }
        int probationPeriod = owner.getProbationPeriod();
        switch (increase) {
            case SHORT:
                owner.setProbationPeriod(probationPeriod + 14);
                break;
            case LONG:
                owner.setProbationPeriod(probationPeriod + 30);
                break;
        }
        telegramBotSender.send(owner.getChatId(), "Ваш испытательный срок продлен на " + increase.getTitle());
    }

    public void revokeOwnership(Long petId) {
        Pet pet = petService.getPetById(petId);
        checkPet(pet);
        User owner = pet.getOwner();
        checkOwner(owner);
        owner.setPet(null);
        owner.setRole(Role.USER);
        telegramBotSender.send(owner.getChatId(), "Вы не прошли испытательный срок. Скоро с Вами свяжется волонтер по вопросу возвращения питомца в приют. Ожидайте");
    }

    public void confirmOwnership(Long petId) {
        Pet pet = petService.getPetById(petId);
        checkPet(pet);
        User owner = pet.getOwner();
        checkOwner(owner);
        owner.setProbationPeriod(null);
        petService.removePetById(petId);
        telegramBotSender.send(owner.getChatId(), "Поздравляем! Вы прошли испытательный срок и теперь является полноценным владельцем питомца");
    }

    private void checkPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException(NO_SUCH_PET);
        }
    }

    private void checkOwner(User owner) {
        if (owner == null) {
            throw new NullUserException(NO_OWNER);
        }
    }
}
