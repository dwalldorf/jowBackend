package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.Demo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoRepository extends MongoRepository<Demo, String> {

    List<Demo> findByUserId(final String userId);
}
