package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.UserRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private HttpSession httpSession;

    @Inject
    private PasswordService passwordService;

    public User register(User user) {
        user.setRegistration(new Date());
        user.setSalt(passwordService.createSalt());
        user.setHashedPassword(
                passwordService.hash(user.getPassword().toCharArray(), user.getSalt())
        );

        User persistedUser = userRepository.save(user);
        return getSecureUserCopy(persistedUser);
    }

    public User findByUsernameOrEmail(final String username) {
        return userRepository.findByUsernameOrEmail(username, username);
    }

    public User login(final String username, final String password) {
        User dbUser = findByUsernameOrEmail(username);
        if (dbUser == null) {
            return null;
        }

        boolean passwordMatch = passwordService.isExpectedPassword(password.toCharArray(), dbUser.getSalt(), dbUser.getHashedPassword());
        if (!passwordMatch) {
            logger.info("Login failed for user '{}'", username);
            return null;
        }

        initializeUserSession(dbUser);
        return getCurrentUser();
    }

    public User getSecureUserCopy(final User user) {
        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null,
                null,
                null,
                user.getRegistration(),
                user.getUserSettings()
        );
    }

    private void initializeUserSession(User user) {
        user.setPassword(null);
        user.setSalt(null);

        httpSession.setAttribute("user", user);

        logger.info("User '{}' logged in", user.getUsername());
    }

    public User getCurrentUser() {
        return (User) httpSession.getAttribute("user");
    }

    public void logout() {
        httpSession.invalidate();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
