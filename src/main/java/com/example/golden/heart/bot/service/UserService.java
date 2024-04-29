package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User edit(Long id, User user) {
        return userRepository.findById(id)
                .map(foundUser -> {
                    foundUser.setName(user.getName());
                    foundUser.setChtId(user.getChtId());
                    foundUser.setPet(user.getPet());
                    foundUser.setRole(user.getRole());
                    foundUser.setPhone(user.getPhone());
                    return userRepository.save(foundUser);
                }).orElse(null);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}
