package com.dwalldorf.owbackend.annotation;


import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.exception.AdminRequiredException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.util.RandomUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class RequireRoleInvocationHandlerTest extends BaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RequireRoleInvocationHandler roleInvocationHandler;

    private UserStub userStub = new UserStub(new RandomUtil());

    private static JoinPoint joinPointMock;

    static {
        joinPointMock = Mockito.mock(JoinPoint.class);
        Signature signatureMock = Mockito.mock(Signature.class);

        when(signatureMock.toString()).thenReturn("UserController#getMe()");
        when(joinPointMock.getSignature()).thenReturn(signatureMock);
    }

    @Test(expected = LoginRequiredException.class)
    public void testCheckLoginBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkLoginBefore(joinPointMock);
    }

    @Test
    public void testCheckLoginBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        roleInvocationHandler.checkLoginBefore(joinPointMock);
    }

    @Test(expected = LoginRequiredException.class)
    public void testCheckAdminBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkAdminBefore(joinPointMock);
    }

    @Test(expected = AdminRequiredException.class)
    public void testCheckAdminBefore_AdminRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(false));
        roleInvocationHandler.checkAdminBefore(joinPointMock);
    }

    @Test
    public void testCheckAdminBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(true));
        roleInvocationHandler.checkAdminBefore(joinPointMock);
    }
}