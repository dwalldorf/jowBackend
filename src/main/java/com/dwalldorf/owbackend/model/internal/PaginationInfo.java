package com.dwalldorf.owbackend.model.internal;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationInfo {

    private final static int DEFAULT_OFFSET = 0;
    private final static int DEFAULT_LIMIT = 10;

    private Integer offset;

    private Integer limit;

    public PaginationInfo(Integer offset, Integer limit) {
        if (offset == null) {
            this.offset = DEFAULT_OFFSET;
        } else {
            this.offset = offset;
        }

        if (limit == null) {
            this.limit = DEFAULT_LIMIT;
        } else {
            this.limit = limit;
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Pageable toPageable() {
        return new PageRequest(this.offset, this.limit);
    }
}
