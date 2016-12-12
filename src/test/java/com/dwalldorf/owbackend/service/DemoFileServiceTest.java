package com.dwalldorf.owbackend.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.DemoFileRepository;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class DemoFileServiceTest extends BaseTest {

    @Mock
    private UserService userService;

    @Mock
    private DemoFileRepository demoFileRepository;

    private DemoFileService demoFileService;

    private UserStub userStub = new UserStub(new RandomUtil());

    @Override
    protected void afterSetup() {
        this.demoFileService = new DemoFileService(demoFileRepository, userService);
    }

    @Test
    public void testSaveSetsUserId() throws Exception {
        User currentUserMock = userStub.createUser();
        DemoFile demoFileMock = Mockito.mock(DemoFile.class);

        when(userService.getCurrentUser()).thenReturn(currentUserMock);

        demoFileService.save(demoFileMock);

        verify(userService).getCurrentUser();
        verify(demoFileMock).setUserId(eq(currentUserMock.getId()));
    }

    @Test(expected = LoginRequiredException.class)
    public void testSaveThrowsNotLoggedInException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        demoFileService.save(new DemoFile());
    }

    @Test
    public void testSave() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        DemoFile demoFile = new DemoFile();
        demoFileService.save(demoFile);

        verify(demoFileRepository).save(eq(demoFile));
    }
}