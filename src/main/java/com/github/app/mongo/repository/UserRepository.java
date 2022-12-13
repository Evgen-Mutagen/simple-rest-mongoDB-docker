package com.github.app.mongo.repository;

import com.github.app.mongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByFirstName(String firstName);

    @Override
    List<User> findAll();
}
