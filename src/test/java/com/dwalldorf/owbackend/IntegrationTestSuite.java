package com.dwalldorf.owbackend;

import com.dwalldorf.owbackend.it.rest.controller.OverwatchVerdictControllerIT;
import com.dwalldorf.owbackend.it.rest.controller.UserControllerIT;
import com.dwalldorf.owbackend.it.rest.controller.VersionControllerIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OverwatchVerdictControllerIT.class,
        UserControllerIT.class,
        VersionControllerIT.class
})
public class IntegrationTestSuite {
}
