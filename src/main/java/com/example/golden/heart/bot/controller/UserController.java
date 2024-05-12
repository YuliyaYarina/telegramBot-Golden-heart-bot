package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Создать пользователя"
    )
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            user = userService.save(user);
        } catch (VolunteerAlreadyAppointedException e) {
            logger.error("Exception: ", e);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(user.toString());
    }

    @Operation(
            summary = "Изменить данные пользователя"
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @RequestBody User user) {
        User foundUser;
        try {
            foundUser = userService.edit(id, user);

        } catch (VolunteerAlreadyAppointedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser.toString());
    }

    @Operation(
            summary = "Получить пользователя по id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(
            summary = "Удалить пользователя"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            userService.removeById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Изменить роль пользователю"
    )
    @PutMapping("/change-role")
    public ResponseEntity<String> changeRole(@RequestParam Long id, @RequestParam Role role) {
        User user;
        try {
            user = userService.changeRole(id, role);
        } catch (IllegalArgumentException e) {
            logger.error("Exception: ", e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (VolunteerAlreadyAppointedException e) {
            logger.error("Exception: ", e);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Роль пользователя " + user.getUserName() + " успешно изменена");
    }

    @Operation(
            summary = "Привязать питомца к пользователю"
    )
    @PutMapping("/set-pet")
    public ResponseEntity<String> setPet(@RequestParam Long userId, @RequestParam Long petId) {
        User user;
        try {
            user = userService.setPet(userId, petId);
        } catch (IllegalArgumentException e) {
            logger.error("Exception: ", e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Пользователь " + user.getUserName() + " теперь является владельцем питомца");
    }

    @Operation(
            summary = "Получить список пользователей по роли"
    )
    @GetMapping
        public ResponseEntity<List<User>> findByRole (@RequestParam Role role){
            List<User> users = userService.findByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(users);
    }
}
