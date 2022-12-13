package com.github.app.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
public class User {
    @Id
    private String userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
