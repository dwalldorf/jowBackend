package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.OverwatchVerdict;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OverwatchVerdictRepository extends MongoRepository<OverwatchVerdict, String> {

    List<OverwatchVerdict> findByUserId(String userId);

    List<OverwatchVerdict> findByCreationDateGreaterThanEqual(Date date);
}
