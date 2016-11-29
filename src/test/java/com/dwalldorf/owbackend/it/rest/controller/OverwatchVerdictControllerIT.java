package com.dwalldorf.owbackend.it.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.owbackend.it.BaseControllerIT;
import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.Date;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class OverwatchVerdictControllerIT extends BaseControllerIT {

    private final static String USER_ID = "userId";

    @MockBean
    private UserService userService;

    @MockBean
    private OverwatchVerdictService verdictService;

    @Test
    public void testPostVerdict_NotLoggedIn() throws Exception {
        OverwatchVerdict verdict = createVerdict();

        doPost("/overwatch/verdicts", verdict)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostVerdict_Success() throws Exception {
        final String persistedVerdictId = "someId";
        final User currentUser = new User();
        currentUser.setId(USER_ID);
        OverwatchVerdict postVerdict = createVerdict();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(verdictService.save(any())).then(invocation -> {
            OverwatchVerdict verdictFromControllerToService = (OverwatchVerdict) invocation.getArguments()[0];
            verdictFromControllerToService.setId(persistedVerdictId);
            return verdictFromControllerToService;
        });

        doPost("/overwatch/verdicts", postVerdict)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(persistedVerdictId)))
                .andExpect(jsonPath("$.userId", is(currentUser.getId()))); // make sure userId gets set
    }

    private OverwatchVerdict createVerdict() {
        return new OverwatchVerdict()
                .setMap(CSGOMap.DE_COBBLESTONE)
                .setAimAssist(true)
                .setVisionAssist(false)
                .setOtherAssist(false)
                .setGriefing(false)
                .setOverwatchDate(new Date())
                .setUserId(null); // userId gets set by application
    }
}
