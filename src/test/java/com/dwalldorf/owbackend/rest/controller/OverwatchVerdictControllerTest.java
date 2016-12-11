package com.dwalldorf.owbackend.rest.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
}