package com.dwalldorf.owbackend.it.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.owbackend.it.BaseControllerIT;
import com.dwalldorf.owbackend.rest.controller.DemoController;
import org.junit.Test;

public class DemoControllerIT extends BaseControllerIT {

    @Test
    public void getDemosByUser_NotLoggedIn() throws Exception {
        doGet(DemoController.URI)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getDemosByUser_Success() throws Exception {

    }
}