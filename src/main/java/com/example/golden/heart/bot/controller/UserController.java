package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.media.MediaType;
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
            summary = "Создать пользователя",
            tags = "Операции с пользователем",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавьте пользователя"
            )
    )
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            user = userService.save(user);
        } catch (VolunteerAlreadyAppointedException e) {
            logger.error("Exception: ", e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Изменить данные пользователя",
            tags = "Операции с пользователем",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Изменяемый пользователь"
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@RequestParam(name = "id пользователя") Long id,
                                           @RequestBody User user) {
        User foundUser;
        try {
            foundUser = userService.edit(id, user);

        } catch (VolunteerAlreadyAppointedException e) {
            logger.error("Exception: ", e);
            return ResponseEntity.badRequest().build();
        }
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser);
    }

    @Operation(
            summary = "Получить пользователя по id",
            tags = "Операции с пользователем"
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@RequestParam(name = "id пользователя") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(
            summary = "Удалить пользователя",
            tags = "Операции с пользователем"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@RequestParam(name = "id пользователя") Long id) {
        User user = userService.getById(id);
        if (user != null) {
            userService.removeById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Изменить роль пользователю",
            tags = "Операции с пользователем"
    )
    @PutMapping("/change-role")
    public ResponseEntity<String> changeRole(@RequestParam(name = "id пользователя") Long id,
                                             @Parameter(description = "Данные которые хотите изменить")@RequestParam Role role) {
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
            summary = "Привязать питомца к пользователю",
            tags = "Операции с пользователем"
    )
    @PutMapping("/set-pet")
    public ResponseEntity<String> setPet(@RequestParam(name = "id пользователя") Long userId,
                                         @RequestParam(name = "id питомца") Long petId) {
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
            summary = "Получить список пользователей по роли",
            tags = "Операции с пользователем"
    )
    @GetMapping("/find-by-role")
        public ResponseEntity<List<User>> findByRole (@Parameter(description = "Выберете роль, по которой хотите получить список пользователей")
                                                          @RequestParam(name = "роль") Role role){
            List<User> users = userService.findByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(users);
    }
}
