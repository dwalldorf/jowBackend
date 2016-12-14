package com.dwalldorf.owbackend.rest.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserProperties;
import java.util.Date;
import org.junit.Test;

public class UserDtoTest extends BaseTest {

    @Test
    public void fromUser() throws Exception {
        User user = new User("id");
        UserProperties properties = user.getUserProperties();

        properties.setUsername("username")
                  .setEmail("test@example.tld")
                  .setPassword("password")
                  .setRegistration(new Date())
                  .getUserSettings().setIsAdmin(true);

        UserDto userDto = UserDto.fromUser(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(properties.getUsername(), userDto.getUsername());
        assertEquals(properties.getEmail(), userDto.getEmail());
        assertEquals(properties.getRegistration(), userDto.getRegistration());
        assertEquals(properties.getUserSettings().isAdmin(), userDto.getUserSettings().isAdmin());

        assertNull(userDto.getPassword());
    }

    @Test
    public void toUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("id");
        userDto.setUsername("username")
               .setEmail("test@example.tld")
               .setPassword("password")
               .setRegistration(new Date())
               .getUserSettings().setIsAdmin(true);

        User user = UserDto.toUser(userDto);
        UserProperties properties = user.getUserProperties();

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), properties.getUsername());
        assertEquals(userDto.getPassword(), properties.getPassword());
        assertEquals(userDto.getEmail(), properties.getEmail());
        assertEquals(userDto.getRegistration(), properties.getRegistration());
        assertEquals(userDto.getUserSettings().isAdmin(), properties.getUserSettings().isAdmin());
    }
}