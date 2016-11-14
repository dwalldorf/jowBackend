package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashMap;
import java.util.Map;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoEvent {

    private int eventId;

    private String name;

    private long timeInRound;

    @JsonDeserialize(as = HashMap.class)
    private Map<String, String> data;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeInRound() {
        return timeInRound;
    }

    public void setTimeInRound(long timeInRound) {
        this.timeInRound = timeInRound;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
