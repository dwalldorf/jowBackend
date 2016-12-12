package com.dwalldorf.owbackend.model.internal;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationInfo {

    public final static Integer PAGE_DEFAULT = 1;
    public final static Integer LIMIT_DEFAULT = 10;
    public final static Integer LIMIT_MAX = 100;

    private Integer page;

    private Integer limit;

    public PaginationInfo(Integer page, Integer limit) {
        setPage(page);
        setLimit(limit);
    }

    public PaginationInfo() {
        this(null, null);
    }

    public Integer getPage() {
        return page;
    }

    public PaginationInfo setPage(Integer page) {
        if (page == null) {
            this.page = PAGE_DEFAULT;
        } else {
            this.page = page;
        }

        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public PaginationInfo setLimit(Integer limit) {
        if (limit == null || limit == 0) {
            this.limit = LIMIT_DEFAULT;
        } else {
            if (limit > LIMIT_MAX) {
                this.limit = LIMIT_MAX;
            } else {
                this.limit = limit;
            }
        }
        return this;
    }

    public Pageable toPageable() {
        return new PageRequest(this.page, this.limit);
    }
}
