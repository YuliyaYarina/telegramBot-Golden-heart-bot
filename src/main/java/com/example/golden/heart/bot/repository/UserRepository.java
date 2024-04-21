package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
