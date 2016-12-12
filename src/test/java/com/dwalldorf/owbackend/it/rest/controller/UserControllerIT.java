package com.dwalldorf.owbackend.it.rest.controller;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.owbackend.it.BaseControllerIT;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserSettings;
import com.dwalldorf.owbackend.rest.controller.UserController;
import com.dwalldorf.owbackend.rest.dto.LoginDto;
import com.dwalldorf.owbackend.service.UserService;
import java.util.Arrays;
import java.util.Date;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserControllerIT extends BaseControllerIT {

    private final static String URI = UserController.URI_BASE;
    private final static String URI_ME = URI + UserController.URI_ME;
    private final static String URI_LOGIN = URI + UserController.URI_LOGIN;
    private final static String URI_LOGOUT = URI + UserController.URI_LOGOUT;

    @MockBean
    private UserService userServiceMock;

    @Test
    public void testGetMe_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get(URI_ME))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMe_Success() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doGet(URI_ME)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.userProperties.username", is(user.getUserProperties().getUsername())))
                .andExpect(jsonPath("$.userProperties.email", is(user.getUserProperties().getEmail())))
                .andExpect(jsonPath("$.userProperties.registration", anything()))
                .andExpect(jsonPath("$.userProperties.userSettings", anything()));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");

        doPost(URI_LOGIN, loginDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        LoginDto loginDto = createLoginDto();
        User user = createUser();

        when(userServiceMock.login(loginDto.getUsername(), loginDto.getPassword())).thenReturn(user);

        doPost(URI_LOGIN, loginDto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.userProperties.username", is(user.getUserProperties().getUsername())));
    }

    @Test
    public void testLogout_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);
        doPost(URI_LOGOUT)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLogout_Success() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doPost(URI_LOGOUT)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers_NotLoggedIn() throws Exception {
        when(userServiceMock.getCurrentUser()).thenReturn(null);

        doGet(URI)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_NoAdmin() throws Exception {
        User user = createUser();
        when(userServiceMock.getCurrentUser()).thenReturn(user);

        doGet(URI)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        User user = createUser(true);
        User someOtherUser = createUser();
        someOtherUser.setId("someotherId");
        someOtherUser.getUserProperties().setUsername("someOtherUsername");

        when(userServiceMock.getCurrentUser()).thenReturn(user);
        when(userServiceMock.getUsers()).thenReturn(Arrays.asList(user, someOtherUser));

        doGet(URI)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.entries", IsCollectionWithSize.hasSize(2)));
    }

    private User createUser() {
        return createUser(false);
    }

    private User createUser(final boolean isAdmin) {
        UserSettings userSettings = new UserSettings();
        userSettings.setIsAdmin(isAdmin);

        User user = new User();
        user.getUserProperties()
            .setUsername("testUser")
            .setEmail("user@provider.test")
            .setRegistration(new Date())
            .setUserSettings(userSettings);

        return user;
    }

    private LoginDto createLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");

        return loginDto;
    }
}
