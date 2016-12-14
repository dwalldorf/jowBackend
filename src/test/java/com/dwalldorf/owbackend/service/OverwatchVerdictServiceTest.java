package com.dwalldorf.owbackend.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.OverwatchVerdictRepository;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import java.util.Date;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

public class OverwatchVerdictServiceTest extends BaseTest {

    @Mock
    private OverwatchVerdictRepository verdictRepository;

    @Mock
    private UserService userService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private OverwatchVerdictService verdictService;

    private final UserStub userStub = new UserStub(new RandomUtil());

    @Override
    protected void afterSetup() {
        this.verdictService = new OverwatchVerdictService(eventPublisher, userService, verdictRepository);
    }

    @Test
    public void saveSetUserId() throws Exception {
        User currentUserMock = userStub.createUser();

        when(userService.getCurrentUser()).thenReturn(currentUserMock);
        OverwatchVerdict verdictMock = Mockito.mock(OverwatchVerdict.class);

        verdictService.save(verdictMock);

        verify(userService).getCurrentUser();
        verify(verdictMock).setUserId(eq(currentUserMock.getId()));
    }

    @Test(expected = LoginRequiredException.class)
    public void saveThrowsNotLoggedIn() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        verdictService.save(new OverwatchVerdict());
    }

    @Test
    public void saveSetsCreationDate() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        OverwatchVerdict verdictMock = Mockito.mock(OverwatchVerdict.class);

        verdictService.save(verdictMock);
        verify(verdictMock).setCreationDate(any(Date.class));
    }

    @Test
    public void testSave() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        OverwatchVerdict overwatchVerdict = new OverwatchVerdict();
        verdictService.save(overwatchVerdict);

        verify(verdictRepository).save(eq(overwatchVerdict));
    }
}