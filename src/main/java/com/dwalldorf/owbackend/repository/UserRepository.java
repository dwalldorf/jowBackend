package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(final String username);

    User findByEmail(final String email);

    User findByUsernameOrEmail(final String username, final String email);

}
