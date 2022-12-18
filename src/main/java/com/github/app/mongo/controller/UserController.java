package com.github.app.mongo.controller;


import com.github.app.mongo.model.Role;
import com.github.app.mongo.model.User;
import com.github.app.mongo.repository.UserRepository;
import com.github.app.mongo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
@Tag(
        name = "Пользователи",
        description = "Все методы для работы с пользователями системы"
)
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final AuthService authService;

    @GetMapping(value = "")
    @Operation(summary = "Получить информацию о всех пользователях")
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        initializationUsers();
        return userRepository.findAll();
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Добавить нового пользователя")
    public User addNewUsers(@Valid @RequestBody User user) {
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    @PutMapping(value = "/update/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Обновить информацию о пользователе")
    public void update(@Parameter(description = "id пользователя") @PathVariable String userId, @Valid @RequestBody User user) {
        LOG.info("update {} with id={}", user, userId);
        User updateUser = userRepository.findById(user.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("model doesn't exist"));
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(updateUser);
    }

    @GetMapping(value = "/id/{userId}")
    @Operation(summary = "Получить информацию о пользователе по его id")
    public Optional<User> getUserById(@Parameter(description = "id пользователя") @PathVariable String userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId);
    }

    @GetMapping(value = "/name/{firstName}")
    @Operation(summary = "Получить информацию о пользователе по его имени")
    public List<User> getUserByName(@Parameter(description = "имя пользователя") @PathVariable String firstName) {
        LOG.info("Getting user with firstName: {}.", firstName);
        return userRepository.findByFirstName(firstName);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить пользователя по его по его id")
    public void delete(@PathVariable String userId) {
        LOG.info("delete menu with id={}", userId);
        userRepository.deleteById(userId);
    }

    public void initializationUsers() {
        addNewUsers(new User("45", "user1", "user2", "user1@mail.ru",
                "867677686", Collections.singleton(Role.USER),"qwerty"));
        addNewUsers(new User("46", "user2", "user2", "user2@mail.ru",
                "43534534", Collections.singleton(Role.ADMIN), "qwerty"));
    }
}


