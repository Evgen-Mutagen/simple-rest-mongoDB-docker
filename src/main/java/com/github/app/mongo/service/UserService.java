package com.github.app.mongo.service;

import com.github.app.mongo.model.User;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final List<User> users;

    public Optional<User> getByLogin(@NonNull String email) {
        return users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
    }
}
