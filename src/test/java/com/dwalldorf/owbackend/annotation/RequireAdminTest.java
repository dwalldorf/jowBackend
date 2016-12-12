package com.dwalldorf.owbackend.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.AdminRequiredException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class RequireAdminTest extends BaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RequireRoleInvocationHandler roleInvocationHandler;

    private final UserStub userStub = new UserStub(new RandomUtil());

    @Test(expected = LoginRequiredException.class)
    public void testCheckAdminBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test(expected = AdminRequiredException.class)
    public void testCheckAdminBefore_AdminRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(false));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test
    public void testCheckAdminBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(true));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }
}
