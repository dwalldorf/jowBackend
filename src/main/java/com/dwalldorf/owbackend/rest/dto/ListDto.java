package com.dwalldorf.owbackend.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class ListDto<T extends Serializable> implements Serializable {

    private List<T> entries;

    public ListDto(List<T> entries) {
        this.entries = entries;
    }

    @JsonProperty
    public List<T> getEntries() {
        return entries;
    }

    @JsonProperty
    public int getCount() {
        if (entries != null) {
            return entries.size();
        }
        return 0;
    }
}
