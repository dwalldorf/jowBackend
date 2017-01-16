package com.dwalldorf.owbackend.rest.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.DemoMatchInfo;
import org.junit.Test;

public class DemoDtoTest extends BaseTest {

    @Test
    public void fromEntity() throws Exception {
        Demo entity = new Demo()
                .setId("someId")
                .setUserId("someUserId")
                .setAnalyzed(true)
                .setMatchInfo(new DemoMatchInfo());

        DemoDto dto = DemoDto.fromEntity(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUserId(), dto.getUserId());
        assertEquals(entity.isAnalyzed(), dto.isAnalyzed());
        assertNotNull(dto.getMatchInfo());
    }

    @Test
    public void testFromEntityNull() throws Exception {
        assertNull(DemoDto.fromEntity(null));
    }
}