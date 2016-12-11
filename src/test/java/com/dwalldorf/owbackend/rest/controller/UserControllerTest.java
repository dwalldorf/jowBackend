package com.dwalldorf.owbackend.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.repository.UserRepository;
import com.dwalldorf.owbackend.rest.dto.LoginDto;
import com.dwalldorf.owbackend.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

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
        ReflectionTestUtils.setField(userService, "userRepository", Mockito.mock(UserRepository.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoPassword() throws Exception {
        ReflectionTestUtils.setField(userService, "userRepository", Mockito.mock(UserRepository.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }
}
