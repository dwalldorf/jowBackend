package com.dwalldorf.owbackend.rest.dto;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.owbackend.BaseTest;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ListDtoTest extends BaseTest {

    @Test
    public void testCount() throws Exception {
        List<String> strings = Arrays.asList("one", "two");
        ListDto<String> listDto = new ListDto<>(strings);

        assertEquals(2, listDto.getCount());
    }

    @Test
    public void testGetEntries() throws Exception {
        List<String> strings = Arrays.asList("one", "two");
        ListDto<String> listDto = new ListDto<>(strings);

        assertEquals(strings.get(0), listDto.getEntries().get(0));
        assertEquals(strings.get(1), listDto.getEntries().get(1));
    }
}