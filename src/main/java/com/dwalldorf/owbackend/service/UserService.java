package com.dwalldorf.owbackend.service;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.UserRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Log
    private Logger logger;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private final PasswordService passwordService;

    @Inject
    public UserService(UserRepository userRepository, HttpSession httpSession, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
        this.passwordService = passwordService;
    }

    @Transactional
    public User register(User user) {
        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (byUsernameOrEmail != null) {
            throw new InvalidInputException("username or email already in use");
        }

        user.setRegistration(new Date());
        user.setSalt(passwordService.createSalt());
        user.setHashedPassword(
                passwordService.hash(user.getPassword().toCharArray(), user.getSalt())
        );

        User persistedUser = userRepository.save(user);

        logger.info(appInfoMarker, "User {} registered", persistedUser.getUsername());
        return getSecureUserCopy(persistedUser);
    }

    public User findById(final String userId) {
        return userRepository.findOne(userId);
    }

    public User login(final String username, final String password) {
        User dbUser = findByUsernameOrEmail(username);
        if (dbUser == null) {
            return null;
        }

        boolean passwordMatch = passwordService.isExpectedPassword(password.toCharArray(), dbUser.getSalt(), dbUser.getHashedPassword());
        if (!passwordMatch) {
            logger.info(appInfoMarker, "Login failed for user '{}'", username);
            return null;
        }

        initializeUserSession(dbUser);
        return getCurrentUser();
    }

    private User findByUsernameOrEmail(final String username) {
        return userRepository.findByUsernameOrEmail(username, username);
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

        logger.info(appInfoMarker, "User '{}' logged in", user.getUsername());
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
