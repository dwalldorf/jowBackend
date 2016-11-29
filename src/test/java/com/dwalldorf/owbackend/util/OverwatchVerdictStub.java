package com.dwalldorf.owbackend.util;

import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import java.util.Date;

public class OverwatchVerdictStub {

    public static OverwatchVerdict createVerdict(
            String id,
            String userId,
            CSGOMap map,
            Date overwatchDate,
            boolean aimAssist,
            boolean visionAssist,
            boolean otherAssist,
            boolean griefing
    ) {
        return new OverwatchVerdict()
                .setId(id)
                .setUserId(userId)
                .setMap(map)
                .setOverwatchDate(overwatchDate)
                .setAimAssist(aimAssist)
                .setVisionAssist(visionAssist)
                .setOtherAssist(otherAssist)
                .setGriefing(griefing);
    }
}
