package com.dwalldorf.owbackend.rest.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.DemoMatchInfo;
import java.util.Date;
import org.junit.Test;

public class DemoMatchInfoDtoTest extends BaseTest {

    @Test
    public void fromEntity() throws Exception {
        DemoMatchInfo entity = new DemoMatchInfo()
                .setMatchId("matchId")
                .setDate(new Date())
                .setDemoType("GOTV")
                .setDuration(7421L)
                .setMap(CSGOMap.de_aztec)
                .setScoreTeam1(16)
                .setScoreTeam2(9)
                .setScoreHalftimeTeam1(11)
                .setScoreHalftimeTeam2(3);

        DemoMatchInfoDto dto = DemoMatchInfoDto.fromEntity(entity);

        assertEquals(entity.getMatchId(), dto.getMatchId());
        assertEquals(entity.getDate(), dto.getDate());
        assertEquals(entity.getDemoType(), dto.getDemoType());
        assertEquals(entity.getMap(), dto.getMap());
        assertEquals(entity.getScoreTeam1(), dto.getScoreTeam1());
        assertEquals(entity.getScoreTeam2(), dto.getScoreTeam2());
    }

    @Test
    public void testEntityNull() throws Exception {
        assertNull(DemoMatchInfoDto.fromEntity(null));
    }
}