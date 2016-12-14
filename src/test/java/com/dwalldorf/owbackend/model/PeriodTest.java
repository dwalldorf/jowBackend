package com.dwalldorf.owbackend.model;

import static com.dwalldorf.owbackend.model.OverwatchUserScore.Period.fromInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import org.junit.Test;

public class PeriodTest extends BaseTest {

    @Test(expected = NotFoundException.class)
    public void fromIntThrowsNotFound() throws Exception {
        Period validPeriod = fromInt(1);

        assertNotNull(validPeriod);
        assertEquals(1, validPeriod.getDays());

        fromInt(1337);
    }
}