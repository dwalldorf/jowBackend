package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserProperties_Username(final String username);

    User findByUserProperties_Email(final String email);

    User findByUserProperties_UsernameOrUserProperties_Email(final String username, final String email);

}
