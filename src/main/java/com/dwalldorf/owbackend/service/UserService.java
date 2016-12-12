package com.dwalldorf.owbackend.service;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserProperties;
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
        UserProperties properties = user.getUserProperties();

        User byUsernameOrEmail = userRepository.findByUserProperties_UsernameOrUserProperties_Email(properties.getUsername(), properties.getEmail());
        if (byUsernameOrEmail != null) {
            throw new InvalidInputException("username or email already in use");
        }

        properties.setRegistration(new Date())
                  .setSalt(passwordService.createSalt())
                  .setHashedPassword(
                          passwordService.hash(properties.getPassword().toCharArray(), properties.getSalt())
                  );

        User persistedUser = userRepository.save(user);

        logger.info(appInfoMarker, "User {} registered", properties.getUsername());
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

        UserProperties properties = dbUser.getUserProperties();
        boolean passwordMatch = passwordService.isExpectedPassword(password.toCharArray(), properties.getSalt(), properties.getHashedPassword());
        if (!passwordMatch) {
            logger.info(appInfoMarker, "Login failed for user '{}'", username);
            return null;
        }

        initializeUserSession(dbUser);
        return getCurrentUser();
    }

    private User findByUsernameOrEmail(final String username) {
        return userRepository.findByUserProperties_UsernameOrUserProperties_Email(username, username);
    }

    public User getSecureUserCopy(final User user) {
        user.getUserProperties()
            .setPassword(null)
            .setSalt(null)
            .setHashedPassword(null);

        return user;
    }

    private void initializeUserSession(User user) {
        user = getSecureUserCopy(user);
        httpSession.setAttribute("user", user);

        logger.info(appInfoMarker, "User '{}' logged in", user.getUserProperties().getUsername());
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
