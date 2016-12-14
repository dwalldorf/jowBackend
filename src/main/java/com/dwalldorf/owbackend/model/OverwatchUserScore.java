package com.dwalldorf.owbackend.model;

import static org.springframework.data.mongodb.core.index.IndexDirection.ASCENDING;

import com.dwalldorf.owbackend.exception.NotFoundException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = OverwatchUserScore.COLLECTION_NAME)
public class OverwatchUserScore implements Serializable {

    public final static String COLLECTION_NAME = "overwatch_scores";

    private String id;

    @Indexed(direction = ASCENDING)
    private Integer period;

    private String userId;

    @Indexed
    private Date calculated;

    @Indexed(direction = ASCENDING)
    private Integer position;

    private Integer verdicts = 0;

    public enum Period {
        DAILY(1),
        WEEKLY(7),
        MONTHLY(30);

        private final static Map<Integer, Period> MAP;

        static {
            MAP = Arrays.stream(values())
                        .collect(Collectors.toMap(Period::getDays, e -> e));
        }

        private int daysBack;

        Period(int i) {
            this.daysBack = i;
        }

        public static Period fromInt(int periodValue) throws NotFoundException {
            Period period = MAP.get(periodValue);
            if (period == null) {
                throw new NotFoundException("No such period: " + periodValue);
            }
            return period;
        }

        public int getDays() {
            return daysBack;
        }
    }

    public String getId() {
        return id;
    }

    public OverwatchUserScore setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public OverwatchUserScore setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OverwatchUserScore setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Date getCalculated() {
        return calculated;
    }

    public OverwatchUserScore setCalculated(Date calculated) {
        this.calculated = calculated;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public OverwatchUserScore setPosition(Integer position) {
        this.position = position;
        return this;
    }

    public Integer getVerdicts() {
        return verdicts;
    }

    public OverwatchUserScore setVerdicts(int verdicts) {
        this.verdicts = verdicts;
        return this;
    }

    public OverwatchUserScore addVerdict() {
        this.verdicts++;
        return this;
    }
}
