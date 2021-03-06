package com.dwalldorf.owbackend.stub;

import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserSettings;
import com.dwalldorf.owbackend.util.RandomUtil;
import java.util.Date;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class UserStub {

    private final RandomUtil randomUtil;

    @Inject
    public UserStub(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    public User createUser(String userId, String username, String email, Date registration, boolean isAdmin) {
        UserSettings userSettings = new UserSettings()
                .setIsAdmin(isAdmin);

        User user = new User(userId);
        user.getUserProperties()
            .setUsername(username)
            .setEmail(email)
            .setRegistration(registration)
            .setUserSettings(userSettings);

        return user;
    }

    public User createUser(boolean isAdmin) {
        String mail = randomUtil.randomString(8) + "@" + randomUtil.randomString(8) + ".com";


        return createUser(
                randomUtil.randomString(32),
                randomUtil.randomString(20),
                mail,
                new Date(),
                isAdmin
        );
    }

    public User createUser() {
        return createUser(false);
    }
}
