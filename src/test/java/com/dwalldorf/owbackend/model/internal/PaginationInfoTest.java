package com.dwalldorf.owbackend.model.internal;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.owbackend.BaseTest;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

public class PaginationInfoTest extends BaseTest {

    @Test
    public void testDefaults() throws Exception {
        PaginationInfo paginationInfo = new PaginationInfo();

        assertEquals(PaginationInfo.PAGE_DEFAULT, paginationInfo.getPage());
        assertEquals(PaginationInfo.LIMIT_DEFAULT, paginationInfo.getLimit());
    }

    @Test
    public void testMaxLimit() throws Exception {
        PaginationInfo byConstructor = new PaginationInfo(0, 50000);

        PaginationInfo bySetter = new PaginationInfo().setLimit(500000);

        assertEquals(PaginationInfo.LIMIT_MAX, byConstructor.getLimit());
        assertEquals(PaginationInfo.LIMIT_MAX, bySetter.getLimit());
    }

    @Test
    public void testToPageable() throws Exception {
        final Integer page = 1;
        final Integer limit = 20;

        PaginationInfo paginationInfo = new PaginationInfo(page, limit);
        Pageable pageable = paginationInfo.toPageable();

        assertEquals(page.longValue(), pageable.getPageNumber());
        assertEquals(limit.longValue(), pageable.getPageSize());
    }
}