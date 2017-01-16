package com.dwalldorf.owbackend.model;

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
    private byte[] hashedPassword;

    @NotEmpty
    private byte[] salt;

    @NotEmpty
    private Date registration;

    private Date firstLogin;

    private Date lastLogin;

    private Boolean confirmedEmail = false;

    @Reference
    private UserSettings userSettings;

    private Boolean hasPostedVerdict = false;

    private Boolean hasUploadedDemo = false;

    public UserProperties() {
        this.userSettings = new UserSettings();
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

    public Date getFirstLogin() {
        return firstLogin;
    }

    public UserProperties setFirstLogin(Date firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public UserProperties setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public Boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public UserProperties setConfirmedEmail(Boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
        return this;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public UserProperties setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
        return this;
    }

    public Boolean hasPostedVerdict() {
        return hasPostedVerdict;
    }

    public UserProperties setHasPostedVerdict() {
        return setHasPostedVerdict(true);
    }

    public UserProperties setHasPostedVerdict(Boolean hasPostedVerdict) {
        this.hasPostedVerdict = hasPostedVerdict;
        return this;
    }

    public Boolean hasUploadDemo() {
        return hasUploadedDemo;
    }

    public UserProperties setHasUploadedDemo(Boolean hasUploadedDemo) {
        this.hasUploadedDemo = hasUploadedDemo;
        return this;
    }
}
