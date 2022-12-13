package com.github.app.mongo.controller;


import com.github.app.mongo.model.User;
import com.github.app.mongo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
@AllArgsConstructor
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        initializationUsers();
        return userRepository.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        LOG.info("Saving user.");
        User newUser = userRepository.save(new User(user.getUserId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber()));
        return userRepository.save(newUser);
    }

    @RequestMapping(value = "/id/{userId}", method = RequestMethod.GET)
    public Optional<User> getUserById(@PathVariable String userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/name/{firstName}", method = RequestMethod.GET)
    public List<User> getUserByName(@PathVariable String firstName) {
        LOG.info("Getting user with firstName: {}.", firstName);
        return userRepository.findByFirstName(firstName);
    }

    public void initializationUsers() {
        addNewUsers(new User("45", "user1", "user2", "user1@mail.ru", "867677686"));
        addNewUsers(new User("46", "user2", "user2", "user2@mail.ru", "43534534"));
        addNewUsers(new User("47", "user3", "user2", "user2@mail.ru", "6666666666"));
    }
}



