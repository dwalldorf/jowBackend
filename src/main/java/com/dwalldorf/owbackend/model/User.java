package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User implements Serializable {

    @Id
    private String id;

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

    public User() {
        this.userSettings = new UserSettings();
    }

    public User(String id, String username, String email, String password, byte[] hashedPassword, byte[] salt, Date registration, UserSettings userSettings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.registration = registration;
        this.userSettings = userSettings;

        if (this.userSettings == null) {
            this.userSettings = new UserSettings();
        }
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public User setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public byte[] getSalt() {
        return salt;
    }

    public User setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public Date getRegistration() {
        return registration;
    }

    public User setRegistration(Date registration) {
        this.registration = registration;
        return this;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public User setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
        return this;
    }
}
