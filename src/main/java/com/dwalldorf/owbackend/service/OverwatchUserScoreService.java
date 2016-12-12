package com.dwalldorf.owbackend.service;

import static java.time.temporal.ChronoUnit.DAYS;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.internal.PaginationInfo;
import com.dwalldorf.owbackend.repository.OverwatchUserScoreRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.bson.Document;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OverwatchUserScoreService {

    @Log
    private Logger logger;

    private final static String map = "function () {" +
            "    emit(this.userId, 1);" +
            "}";

    private final static String reduce = "function(k, values) {" +
            "    var sum = 0;" +
            "    for (var i in values) {" +
            "        sum = sum + 1;" +
            "    }" +
            "    return sum;" +
            "}";

    private final MongoClient mongoClient;

    private final OverwatchUserScoreRepository scoreRepository;

    @Value("${mongo.db}")
    private String dbName;

    @Inject
    public OverwatchUserScoreService(MongoClient mongoClient, OverwatchUserScoreRepository scoreRepository) {
        this.mongoClient = mongoClient;
        this.scoreRepository = scoreRepository;
    }

    public OverwatchUserScore findByUserIdAndPeriod(User userId, Period period) {
        return scoreRepository.findOneByUserIdAndPeriod(userId.getId(), period.getDays());
    }

    public List<OverwatchUserScore> findByHigherThanUser(User user, Period period, PaginationInfo paginationInfo) {
        OverwatchUserScore score = findByUserIdAndPeriod(user, period);

        return scoreRepository.findByPeriodAndPositionGreaterThanOrderByPositionAsc(
                period.getDays(),
                score.getPosition(),
                paginationInfo.toPageable()
        );
    }

    public List<OverwatchUserScore> findByLowerThanUser(User userId, Period period, PaginationInfo paginationInfo) {
        OverwatchUserScore score = findByUserIdAndPeriod(userId, period);
        return scoreRepository.findByPeriodAndPositionLessThanOrderByPositionAsc(
                period.getDays(),
                score.getPosition(),
                paginationInfo.toPageable()
        );
    }

    @Transactional
    public Integer reprocessUserScores(Period period) {
        List<OverwatchUserScore> newScoresByPeriod = getUserScoresByPeriod(period);

        if (newScoresByPeriod.size() == 0) {
            return 0;
        }

        List<OverwatchUserScore> oldScoresByPeriod = scoreRepository.findByPeriod(period.getDays());
        if (oldScoresByPeriod.size() > 0) {
            scoreRepository.delete(oldScoresByPeriod);
        }

        scoreRepository.save(newScoresByPeriod);
        return newScoresByPeriod.size();
    }

    public Optional<OverwatchUserScore> getLatestScoreByPeriod(Period period) {
        PageRequest pageRequest = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "calculated"));
        List<OverwatchUserScore> result = scoreRepository.findByPeriod(period.getDays(), pageRequest);

        if (result.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(result.iterator().next());
    }

    private List<OverwatchUserScore> getUserScoresByPeriod(Period period) {
        List<OverwatchUserScore> retVal = new ArrayList<>();

        BasicDBObject query = new BasicDBObject();
        Date beforeDate = Date.from(Instant.now().minus(period.getDays(), DAYS));
        query.put("creationDate", BasicDBObjectBuilder.start("$gte", beforeDate).get());

        try {
            MongoCollection<Document> collection = mongoClient.getDatabase(dbName)
                                                              .getCollection(OverwatchVerdict.COLLECTION_NAME);

            collection.mapReduce(map, reduce)
                      .filter(query)
                      .forEach((Block<Document>) doc -> {
                          String userId = doc.getString("_id");
                          Double verdicts = doc.getDouble("value");

                          if (userId != null && verdicts != null) {
                              OverwatchUserScore score = new OverwatchUserScore();
                              score.setUserId(userId)
                                   .setVerdicts(verdicts.intValue())
                                   .setCalculated(new Date())
                                   .setPeriod(period.getDays());

                              retVal.add(score);
                          }
                      });
            retVal.sort((o1, o2) -> o2.getVerdicts().compareTo(o1.getVerdicts()));

            int position = 1;
            for (OverwatchUserScore overwatchUserScore : retVal) {
                overwatchUserScore.setPosition(position);
                position++;
            }
        } catch (MongoCommandException ex) {
            logger.error(ex.getMessage());
        }

        return retVal;
    }
}
