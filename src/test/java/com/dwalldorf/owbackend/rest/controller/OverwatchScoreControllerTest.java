package com.dwalldorf.owbackend.rest.controller;

import static com.dwalldorf.owbackend.model.OverwatchUserScore.Period.DAILY;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class OverwatchScoreControllerTest extends BaseTest {

    @Mock
    private UserService userService;

    @Mock
    private OverwatchUserScoreService scoreService;

    @InjectMocks
    private OverwatchScoreController scoreController;

    @Test(expected = NotFoundException.class)
    public void testGetByUser_ThrowsNotFound() {
        scoreController.getByUser("noSuchUserId", DAILY.getDays());
    }

    @Test(expected = NotFoundException.class)
    public void testGetHigherThanUser_ThrowsNotFound() {
        scoreController.getHigherThanUser("noSucherUserId", DAILY.getDays(), null, null);
    }

    @Test(expected = NotFoundException.class)
    public void getLowerThanUser_ThrowsNotFound() {
        scoreController.getLowerThanUser("noSucherUserId", DAILY.getDays(), null, null);
    }
}