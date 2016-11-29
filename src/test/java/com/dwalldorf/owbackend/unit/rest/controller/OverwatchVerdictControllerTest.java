package com.dwalldorf.owbackend.unit.rest.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.rest.controller.OverwatchVerdictController;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.unit.BaseTest;
import com.dwalldorf.owbackend.util.UserStub;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class OverwatchVerdictControllerTest extends BaseTest {

    @Mock
    private OverwatchVerdictService verdictService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OverwatchVerdictController verdictController;

    @Test(expected = NotFoundException.class)
    public void testGetUserVerdicts_NoSuchUser_ThrowsNotFoundException() throws Exception {
        final String noSuchUserId = "someId";
        when(userService.findById(eq(noSuchUserId))).thenReturn(null);

        verdictController.getUserVerdicts(noSuchUserId);
    }

    @Test
    public void testPostVerdictSetsUserId() throws Exception {
        OverwatchVerdict verdictMock = Mockito.mock(OverwatchVerdict.class);
        User currentUser = UserStub.createUser();
        when(userService.getCurrentUser()).thenReturn(currentUser);

        verdictController.postVerdict(verdictMock);

        verify(userService, atLeastOnce()).getCurrentUser();
        verify(verdictMock).setUserId(eq(currentUser.getId()));
    }
}