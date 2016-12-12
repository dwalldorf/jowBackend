package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

@Embedded
public class UserProperties implements Serializable {

    @NotEmpty
    @Size(min = 5, max = 40)
    @Indexed(unique = true)
    private String username;

    @Email
    @Indexed
    private String email;

    @Transient
    private String password;

    @NotEmpty
    @JsonIgnore
    private byte[] hashedPassword;

    @NotEmpty
    @JsonIgnore
    private byte[] salt;

    @NotEmpty
    private Date registration;

    @Reference
    private UserSettings userSettings;

    public UserProperties() {
        this.userSettings = new UserSettings();
    }

    public UserProperties(String username, String email, String password, byte[] hashedPassword, byte[] salt, Date registration, UserSettings userSettings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.registration = registration;

        if (userSettings == null) {
            this.userSettings = new UserSettings();
        } else {
            this.userSettings = userSettings;
        }
    }

    public String getUsername() {
        return username;
    }

    public UserProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProperties setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public UserProperties setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public byte[] getSalt() {
        return salt;
    }

    public UserProperties setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public Date getRegistration() {
        return registration;
    }

    public UserProperties setRegistration(Date registration) {
        this.registration = registration;
        return this;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public UserProperties setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
        return this;
    }

}
