package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoEvent implements Serializable {

    private Integer eventId;

    private String name;

    private Long timeInRound;

    @JsonDeserialize(as = HashMap.class)
    private Map<String, String> data;

    public Integer getEventId() {
        return eventId;
    }

    public DemoEvent setEventId(int eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DemoEvent setName(String name) {
        this.name = name;
        return this;
    }

    public Long getTimeInRound() {
        return timeInRound;
    }

    public DemoEvent setTimeInRound(long timeInRound) {
        this.timeInRound = timeInRound;
        return this;
    }

    public Map<String, String> getData() {
        return data;
    }

    public DemoEvent setData(Map<String, String> data) {
        this.data = data;
        return this;
    }
}
