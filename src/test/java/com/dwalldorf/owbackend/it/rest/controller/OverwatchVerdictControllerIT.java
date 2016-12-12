package com.dwalldorf.owbackend.it.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.owbackend.it.BaseControllerIT;
import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.rest.controller.OverwatchVerdictController;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.stub.OverwatchVerdictStub;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class OverwatchVerdictControllerIT extends BaseControllerIT {

    private static final String URI_VERDICTS = OverwatchVerdictController.URI_BASE + OverwatchVerdictController.URI_VERDICTS;

    @MockBean
    private UserService userService;

    @MockBean
    private OverwatchVerdictService verdictService;

    private final RandomUtil randomUtil = new RandomUtil();

    private final UserStub userStub = new UserStub(randomUtil);

    private final OverwatchVerdictStub verdictStub = new OverwatchVerdictStub(randomUtil);

    @Test
    public void testPostVerdict_NotLoggedIn() throws Exception {
        OverwatchVerdict verdict = createVerdict();

        doPost(URI_VERDICTS, verdict)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostVerdict_Success() throws Exception {
        final String userId = "userId";
        final String persistedVerdictId = "someId";
        final User currentUser = new User(userId);

        OverwatchVerdict postVerdict = createVerdict();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(verdictService.save(any())).then(invocation -> {
            OverwatchVerdict verdictFromControllerToService = (OverwatchVerdict) invocation.getArguments()[0];
            verdictFromControllerToService.setId(persistedVerdictId);
            verdictFromControllerToService.setUserId(currentUser.getId());
            return verdictFromControllerToService;
        });

        doPost(URI_VERDICTS, postVerdict)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(persistedVerdictId)))
                .andExpect(jsonPath("$.userId", is(currentUser.getId()))); // make sure userId gets set
    }

    @Test
    public void testGetUserVerdicts_NotFound() throws Exception {
        final String userId = "noSuchUserId";
        when(userService.findById(eq(userId))).thenReturn(null);

        doGet(URI_VERDICTS + "/" + userId)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserVerdicts_EmptyResult() throws Exception {
        final String userId = "userWithoutVerdicts";
        User user = userStub.createUser().setId(userId);

        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        when(userService.findById(userId)).thenReturn(user);
        when(verdictService.findByUser(eq(user))).thenReturn(null);

        doGet(URI_VERDICTS + "/" + userId)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserVerdicts_Success() throws Exception {
        final String userId = "someUserId";
        User user = userStub.createUser().setId(userId);

        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        when(userService.findById(userId)).thenReturn(user);

        List<OverwatchVerdict> verdicts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            verdicts.add(verdictStub.createVerdict().setUserId(userId));
        }
        when(verdictService.findByUser(eq(user))).thenReturn(verdicts);

        doGet(URI_VERDICTS + "/" + userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(verdicts.size())))
                .andExpect(jsonPath("$.entries", IsCollectionWithSize.hasSize(verdicts.size())))
                .andExpect(jsonPath("$.entries[0].userId", is(userId)));
    }

    private OverwatchVerdict createVerdict() {
        return new OverwatchVerdict()
                .setMap(CSGOMap.de_cobblestone)
                .setAimAssist(true)
                .setVisionAssist(false)
                .setOtherAssist(false)
                .setGriefing(false)
                .setOverwatchDate(new Date())
                .setUserId(null); // userId gets set by application
    }
}
