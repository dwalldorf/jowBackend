package com.dwalldorf.owbackend.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.UserRepository;
import com.dwalldorf.owbackend.util.PasswordUtil;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final static ObjectId ID = new ObjectId();
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
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSecureUserCopy() {
        User user = createTestUser();

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
        User user = createTestUser();
        user.setRegistration(null);
        assertNull(user.getRegistration());

        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.register(user);

        assertNotNull(registeredUser.getRegistration());
    }

    @Test
    public void testRegisterHashesCorrectly() {
        User user = createTestUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordUtil.createSalt()).thenReturn(SALT);

        userService.register(user);

        Mockito.verify(passwordUtil).createSalt();
        Mockito.verify(passwordUtil).hash(any(), eq(SALT));
    }

    @Test
    public void testRegisterReturnSecureUserCopy() {
        User user = createTestUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordUtil.createSalt()).thenReturn(SALT);

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
        when(userRepository.findByUsernameOrEmail(eq(USERNAME), eq(USERNAME))).thenReturn(createTestUser());

        User retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    private User createTestUser() {
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