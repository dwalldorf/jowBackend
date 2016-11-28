package com.dwalldorf.owbackend.it.rest.controller;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.owbackend.it.BaseControllerIT;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserSettings;
import com.dwalldorf.owbackend.rest.dto.LoginDto;
import com.dwalldorf.owbackend.service.UserService;
import java.util.Arrays;
import java.util.Date;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserControllerIT extends BaseControllerIT {

    @MockBean
    private UserService userServiceMock;

    @Test
    public void testGetMe_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/users/me"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMe_Success() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doGet("/users/me")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.registration", anything()))
                .andExpect(jsonPath("$.userSettings", anything()));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");

        doPost("/users/login", loginDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        LoginDto loginDto = createLoginDto();
        User user = createUser();

        when(userServiceMock.login(loginDto.getUsername(), loginDto.getPassword())).thenReturn(user);

        doPost("/users/login", loginDto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void testLogout_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);
        doPost("/users/logout")
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLogout_Success() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doPost("/users/logout")
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);

        doGet("/users")
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_NoAdmin() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doGet("/users")
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        User user = createUser(true);
        User someOtherUser = createUser();
        someOtherUser.setId("someotherId");
        someOtherUser.setUsername("someOtherUsername");

        when(userServiceMock.getCurrentUser()).thenReturn(user);
        when(userServiceMock.getUsers()).thenReturn(Arrays.asList(user, someOtherUser));

        doGet("/users")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(2)));
    }

    private User createUser() {
        return createUser(false);
    }

    private User createUser(final boolean isAdmin) {
        UserSettings userSettings = new UserSettings();
        userSettings.setIsAdmin(isAdmin);

        User user = new User();
        user.setId("userId");
        user.setUsername("testUser");
        user.setEmail("user@provider.test");
        user.setRegistration(new Date());
        user.setUserSettings(userSettings);

        return user;
    }

    private LoginDto createLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");

        return loginDto;
    }
}
