package com.dwalldorf.owbackend.util;

import static com.dwalldorf.owbackend.util.RandomUtil.randomString;

import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserSettings;
import java.util.Date;

public class UserStub {

    public static User createUser(String userId, String username, String email, Date registration, boolean isAdmin) {
        UserSettings userSettings = new UserSettings()
                .setIsAdmin(isAdmin);

        return new User()
                .setId(userId)
                .setUsername(username)
                .setEmail(email)
                .setRegistration(registration)
                .setUserSettings(userSettings);
    }

    public static User createUser(boolean isAdmin) {
        return createUser(
                randomString(32),
                randomString(20),
                randomString(8) + "@" + randomString(8) + ".com",
                new Date(),
                isAdmin
        );
    }

    public static User createUser() {
        return createUser(false);
    }
}
