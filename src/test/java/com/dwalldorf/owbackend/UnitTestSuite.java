package com.dwalldorf.owbackend;

import com.dwalldorf.owbackend.annotation.RequireRoleInvocationHandlerTest;
import com.dwalldorf.owbackend.rest.controller.DemoFileControllerTest;
import com.dwalldorf.owbackend.rest.controller.OverwatchVerdictControllerTest;
import com.dwalldorf.owbackend.rest.controller.UserControllerTest;
import com.dwalldorf.owbackend.service.DemoFileServiceTest;
import com.dwalldorf.owbackend.service.OverwatchVerdictServiceTest;
import com.dwalldorf.owbackend.service.PasswordServiceTest;
import com.dwalldorf.owbackend.service.UserServiceTest;
import com.dwalldorf.owbackend.util.RandomUtilTest;
import com.dwalldorf.owbackend.worker.OverwatchScoreWorkerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequireRoleInvocationHandlerTest.class,

        DemoFileControllerTest.class,
        OverwatchVerdictControllerTest.class,
        UserControllerTest.class,

        DemoFileServiceTest.class,
        OverwatchVerdictServiceTest.class,
        PasswordServiceTest.class,
        UserServiceTest.class,

        RandomUtilTest.class,

        OverwatchScoreWorkerTest.class
})
public class UnitTestSuite {
}
