package com.dwalldorf.owbackend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.UserRepository;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;

public class UserServiceTest extends BaseTest {

    private final static String ID = "someId";
    private final static String USERNAME = "testUser";
    private final static String EMAIL = "max@mustermann.org";
    private final static Date REGISTRATION = new Date();
    private final static String PASSWORD = "notSoSecurePassword123";
    private final static byte[] SALT = "salt".getBytes();
    private final static byte[] HASHED_PASSWORD = "hashedPassword".getBytes();

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession httpSession;

    @Spy
    private PasswordService passwordService;

    private UserService userService;

    @Override
    protected void afterSetup() {
        this.userService = new UserService(eventPublisher, userRepository, httpSession, passwordService);
    }

    @Test
    public void testGetSecureUserCopy() {
        User user = createUser();

        User secureUserCopy = userService.getSecureUserCopy(user);

        assertEquals(ID, secureUserCopy.getId());
        assertEquals(USERNAME, secureUserCopy.getUserProperties().getUsername());
        assertEquals(EMAIL, secureUserCopy.getUserProperties().getEmail());
        assertEquals(REGISTRATION, secureUserCopy.getUserProperties().getRegistration());

        assertNull(secureUserCopy.getUserProperties().getPassword());
        assertNull(secureUserCopy.getUserProperties().getSalt());
        assertNull(secureUserCopy.getUserProperties().getHashedPassword());
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_UsernameExists() throws Exception {
        final String username = "username";
        User user = new User();
        user.getUserProperties().setUsername(username);

        when(userRepository.findByUserProperties_UsernameOrUserProperties_Email(eq(username), anyString())).thenReturn(new User());

        userService.register(user);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_EmailExists() throws Exception {
        final String email = "test@example.com";
        User user = new User();
        user.getUserProperties().setEmail(email);

        when(userRepository.findByUserProperties_UsernameOrUserProperties_Email(anyString(), anyString())).thenReturn(new User());

        userService.register(user);
    }

    @Test
    public void testRegister_SetsRegistrationDate() throws Exception {
        User user = createUser();
        user.getUserProperties().setRegistration(null);
        assertNull(user.getUserProperties().getRegistration());

        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.register(user);

        assertNotNull(registeredUser.getUserProperties().getRegistration());
    }

    @Test
    public void testRegister_HashesCorrectly() {
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        userService.register(user);

        Mockito.verify(passwordService).createSalt();
        Mockito.verify(passwordService).hash(any(), eq(SALT));
    }

    @Test
    public void testRegister_ReturnSecureUserCopy() {
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        User registeredUser = userService.register(user);

        assertNull(registeredUser.getUserProperties().getPassword());
        assertNull(registeredUser.getUserProperties().getSalt());
        assertNull(registeredUser.getUserProperties().getHashedPassword());
    }

    @Test
    public void testLoginReturnsNullIfUserNotFound() {
        when(userRepository.findByUserProperties_UsernameOrUserProperties_Email(eq(USERNAME), eq(USERNAME))).thenReturn(null);
        User retVal = userService.login(USERNAME, PASSWORD);

        assertNull(retVal);
    }

    @Test
    public void testLoginReturnsNullIfUserFoundButWrongPassword() {
        when(userRepository.findByUserProperties_UsernameOrUserProperties_Email(eq(USERNAME), eq(USERNAME))).thenReturn(createUser());

        User retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    private User createUser() {
        User user = new User(ID);
        user.getUserProperties()
            .setUsername(USERNAME)
            .setEmail(EMAIL)
            .setRegistration(REGISTRATION)
            .setSalt(SALT)
            .setPassword(PASSWORD)
            .setHashedPassword(HASHED_PASSWORD);
        return user;
    }
}