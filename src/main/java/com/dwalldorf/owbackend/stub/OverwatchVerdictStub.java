package com.dwalldorf.owbackend.stub;

import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.util.RandomUtil;
import java.util.Date;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class OverwatchVerdictStub {

    private final RandomUtil randomUtil;

    @Inject
    public OverwatchVerdictStub(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    public OverwatchVerdict createVerdict(
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

    public OverwatchVerdict createVerdict() {
        return createVerdict(
                null,
                randomUtil.randomString(32),
                randomUtil.randomEnum(CSGOMap.class),
                new Date(), // TODO: make random
                randomUtil.randomBoolean(),
                randomUtil.randomBoolean(),
                randomUtil.randomBoolean(),
                randomUtil.randomBoolean()
        );
    }
}
