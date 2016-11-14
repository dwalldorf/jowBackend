package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.DemoFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoFileRepository extends MongoRepository<DemoFile, String> {

}
