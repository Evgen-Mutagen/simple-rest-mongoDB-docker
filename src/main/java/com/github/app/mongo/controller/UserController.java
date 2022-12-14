package com.github.app.mongo.controller;


import com.github.app.mongo.model.User;
import com.github.app.mongo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
@AllArgsConstructor
@Tag(
        name = "Пользователи",
        description = "Все методы для работы с пользователями системы"
)
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Operation(summary = "Получить информацию о всех пользователях")
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        initializationUsers();
        return userRepository.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Operation(summary = "Добавить нового пользователя")
    public User addNewUsers(@Valid @RequestBody User user) {
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    @RequestMapping(value = "/id/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Обновить информацию о пользователе")
    public void update(@Parameter(description = "id пользователя") @PathVariable String userId, @Valid @RequestBody User user) {
        LOG.info("update {} with id={}", user, userId);
        Optional<User> oldUser = userRepository.findById(userId);
        if (oldUser.isPresent()) {
            User updatedUser = oldUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            userRepository.save(updatedUser);
        }
    }


    @RequestMapping(value = "/id/{userId}", method = RequestMethod.GET)
    @Operation(summary = "Получить информацию о пользователе по его id")
    public Optional<User> getUserById(@Parameter(description = "id пользователя") @PathVariable String userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/name/{firstName}", method = RequestMethod.GET)
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
        addNewUsers(new User("45", "user1", "user2", "user1@mail.ru", "867677686"));
        addNewUsers(new User("46", "user2", "user2", "user2@mail.ru", "43534534"));
        addNewUsers(new User("47", "user3", "user2", "user2@mail.ru", "6666666666"));
    }
}



