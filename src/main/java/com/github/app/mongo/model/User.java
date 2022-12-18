package com.github.app.mongo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@Schema(description = "Информация о пользователе")
public class User {
    @Id
    private String userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Set<Role> roles;
    private String password;
}
