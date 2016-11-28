package com.dwalldorf.owbackend.unit.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.rest.controller.UserController;
import com.dwalldorf.owbackend.rest.dto.LoginDto;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.unit.BaseTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserControllerTest extends BaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test(expected = InvalidInputException.class)
    public void testLoginThrowsInvalidInputException() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("");
        loginDto.setPassword("");
        when(userService.login(anyString(), anyString())).thenReturn(null);

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoUsername() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoPassword() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }
}
