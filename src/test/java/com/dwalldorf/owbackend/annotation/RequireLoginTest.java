package com.dwalldorf.owbackend.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class RequireLoginTest extends BaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RequireRoleInvocationHandler roleInvocationHandler;

    private UserStub userStub = new UserStub(new RandomUtil());

    @Test(expected = LoginRequiredException.class)
    public void testCheckLoginBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }

    @Test
    public void testCheckLoginBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }
}
