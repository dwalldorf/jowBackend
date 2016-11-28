package com.dwalldorf.owbackend.unit.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.UserRepository;
import com.dwalldorf.owbackend.service.PasswordService;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.unit.BaseTest;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class UserServiceTest extends BaseTest {

    private final static String ID = "someId";
    private final static String USERNAME = "testUser";
    private final static String EMAIL = "max@mustermann.org";
    private final static Date REGISTRATION = new Date();
    private final static String PASSWORD = "notSoSecurePassword123";
    private final static byte[] SALT = "salt".getBytes();
    private final static byte[] HASHED_PASSWORD = "hashedPassword".getBytes();

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession httpSession;

    @Spy
    private PasswordService passwordService;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSecureUserCopy() {
        User user = createUser();

        User secureUserCopy = userService.getSecureUserCopy(user);

        assertEquals(ID, secureUserCopy.getId());
        assertEquals(USERNAME, secureUserCopy.getUsername());
        assertEquals(EMAIL, secureUserCopy.getEmail());
        assertEquals(REGISTRATION, secureUserCopy.getRegistration());

        assertNull(secureUserCopy.getPassword());
        assertNull(secureUserCopy.getSalt());
        assertNull(secureUserCopy.getHashedPassword());
    }

    @Test
    public void testRegisterSetsRegistrationDate() throws Exception {
        User user = createUser();
        user.setRegistration(null);
        assertNull(user.getRegistration());

        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.register(user);

        assertNotNull(registeredUser.getRegistration());
    }

    @Test
    public void testRegisterHashesCorrectly() {
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        userService.register(user);

        Mockito.verify(passwordService).createSalt();
        Mockito.verify(passwordService).hash(any(), eq(SALT));
    }

    @Test
    public void testRegisterReturnSecureUserCopy() {
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        User registeredUser = userService.register(user);

        assertNull(registeredUser.getPassword());
        assertNull(registeredUser.getSalt());
        assertNull(registeredUser.getHashedPassword());
    }

    @Test
    public void testLoginReturnsNullIfUserNotFound() {
        when(userRepository.findByUsernameOrEmail(eq(USERNAME), eq(USERNAME))).thenReturn(null);
        User retVal = userService.login(USERNAME, PASSWORD);

        assertNull(retVal);
    }

    @Test
    public void testLoginReturnsNullIfUserFoundButWrongPassword() {
        when(userRepository.findByUsernameOrEmail(eq(USERNAME), eq(USERNAME))).thenReturn(createUser());

        User retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    private User createUser() {
        User user = new User();
        user.setId(ID)
            .setUsername(USERNAME)
            .setEmail(EMAIL)
            .setRegistration(REGISTRATION)
            .setSalt(SALT)
            .setPassword(PASSWORD)
            .setHashedPassword(HASHED_PASSWORD);
        return user;
    }
}