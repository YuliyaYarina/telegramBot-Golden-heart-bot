package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);

    Optional<User> findByChatId(Long chatId);
    List<User> findByProbationPeriod(Integer probationPeriod);
}
