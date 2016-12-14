package com.dwalldorf.owbackend;

import com.dwalldorf.owbackend.annotation.LoggerInjectorTest;
import com.dwalldorf.owbackend.annotation.RequireAdminTest;
import com.dwalldorf.owbackend.annotation.RequireLoginTest;
import com.dwalldorf.owbackend.model.PeriodTest;
import com.dwalldorf.owbackend.model.internal.PaginationInfoTest;
import com.dwalldorf.owbackend.rest.controller.DemoFileControllerTest;
import com.dwalldorf.owbackend.rest.controller.OverwatchScoreControllerTest;
import com.dwalldorf.owbackend.rest.controller.OverwatchVerdictControllerTest;
import com.dwalldorf.owbackend.rest.controller.UserControllerTest;
import com.dwalldorf.owbackend.rest.dto.ListDtoTest;
import com.dwalldorf.owbackend.rest.dto.UserDtoTest;
import com.dwalldorf.owbackend.service.DemoFileServiceTest;
import com.dwalldorf.owbackend.service.OverwatchUserScoreServiceTest;
import com.dwalldorf.owbackend.service.OverwatchVerdictServiceTest;
import com.dwalldorf.owbackend.service.PasswordServiceTest;
import com.dwalldorf.owbackend.service.StopWatchTest;
import com.dwalldorf.owbackend.service.UserServiceTest;
import com.dwalldorf.owbackend.util.RandomUtilTest;
import com.dwalldorf.owbackend.worker.OverwatchScoreWorkerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoggerInjectorTest.class,
        RequireAdminTest.class,
        RequireLoginTest.class,

        PaginationInfoTest.class,
        PeriodTest.class,

        DemoFileControllerTest.class,
        OverwatchVerdictControllerTest.class,
        UserControllerTest.class,

        ListDtoTest.class,
        UserDtoTest.class,

        DemoFileServiceTest.class,
        OverwatchScoreControllerTest.class,
        OverwatchUserScoreServiceTest.class,
        OverwatchVerdictServiceTest.class,
        PasswordServiceTest.class,
        StopWatchTest.class,
        UserServiceTest.class,

        RandomUtilTest.class,

        OverwatchScoreWorkerTest.class
})
public class UnitTestSuite {
}
