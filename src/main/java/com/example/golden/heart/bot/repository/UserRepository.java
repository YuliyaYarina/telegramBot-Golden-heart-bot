package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    Collection<User> findByRole(Role role);
}
