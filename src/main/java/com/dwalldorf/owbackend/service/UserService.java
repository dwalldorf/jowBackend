package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.event.user.LoginFailedEvent;
import com.dwalldorf.owbackend.event.user.UserLoginEvent;
import com.dwalldorf.owbackend.event.user.UserLogoutEvent;
import com.dwalldorf.owbackend.event.user.UserRegisterEvent;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserProperties;
import com.dwalldorf.owbackend.repository.UserRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private final PasswordService passwordService;

    @Inject
    public UserService(
            ApplicationEventPublisher eventPublisher,
            UserRepository userRepository,
            HttpSession httpSession,
            PasswordService passwordService) {
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
        this.passwordService = passwordService;
    }

    public User findById(final String userId) {
        return userRepository.findOne(userId);
    }

    public User login(final String username, final String password) {
        User dbUser = findByUsernameOrEmail(username);
        if (dbUser == null) {
            eventPublisher.publishEvent(new LoginFailedEvent(username));
            return null;
        }

        UserProperties properties = dbUser.getUserProperties();
        boolean passwordMatch = passwordService.isExpectedPassword(
                password.toCharArray(),
                properties.getSalt(),
                properties.getHashedPassword()
        );
        if (!passwordMatch) {
            eventPublisher.publishEvent(new LoginFailedEvent(username));
            return null;
        }

        initializeUserSession(dbUser);
        return getCurrentUser();
    }

    @Transactional
    public User register(User user) {
        UserProperties properties = user.getUserProperties();

        User byUsernameOrEmail = userRepository.findByUserProperties_UsernameOrUserProperties_Email(properties.getUsername(), properties.getEmail());
        if (byUsernameOrEmail != null) {
            throw new InvalidInputException("username or email already in use");
        }

        user.setUserProperties(
                properties.setRegistration(new Date())
                          .setSalt(passwordService.createSalt())
                          .setHashedPassword(
                                  passwordService.hash(properties.getPassword().toCharArray(), properties.getSalt())
                          )
        );
        user = userRepository.save(user);

        eventPublisher.publishEvent(new UserRegisterEvent(user));
        return user;
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    private User findByUsernameOrEmail(final String username) {
        return userRepository.findByUserProperties_UsernameOrUserProperties_Email(username, username);
    }

    private void initializeUserSession(User user) {
        httpSession.setAttribute("user", user);

        eventPublisher.publishEvent(new UserLoginEvent(user));
    }

    public User getCurrentUser() {
        return (User) httpSession.getAttribute("user");
    }

    public void logout() {
        eventPublisher.publishEvent(new UserLogoutEvent(getCurrentUser()));
        httpSession.invalidate();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
