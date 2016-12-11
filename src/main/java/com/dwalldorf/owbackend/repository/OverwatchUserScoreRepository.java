package com.dwalldorf.owbackend.repository;

import com.dwalldorf.owbackend.model.OverwatchUserScore;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OverwatchUserScoreRepository extends MongoRepository<OverwatchUserScore, String> {

    List<OverwatchUserScore> findByPeriod(Integer period);

    List<OverwatchUserScore> findByPeriod(Integer period, Pageable pageable);

    List<OverwatchUserScore> findByUserId(String userId);

    List<OverwatchUserScore> findByPeriodAndPositionGreaterThanOrderByPositionAsc(int period, int position, Pageable pageable);

    List<OverwatchUserScore> findByPeriodAndPositionLessThanOrderByPositionAsc(int period, int position, Pageable pageable);

    OverwatchUserScore findOneByUserIdAndPeriod(String userId, Integer period);
}
