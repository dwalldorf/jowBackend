package com.dwalldorf.owbackend.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.dwalldorf.owbackend.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class, TestConfiguration.class})
@ActiveProfiles({TestConfiguration.INTEGRATION_TEST_PROFILE})
public abstract class BaseControllerIT {

    private final static MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    protected MockMvc mockMvc;

    @Inject
    protected WebApplicationContext ctx;

    @Inject
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        afterSetup();
    }

    protected void afterSetup() {
    }

    protected String toJsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    protected ResultActions doGet(final String uri) throws Exception {
        return mockMvc.perform(get(uri)
                .accept(CONTENT_TYPE));
    }

    protected ResultActions doPost(final String uri) throws Exception {
        return doPost(uri, null);
    }

    protected ResultActions doPost(final String uri, final Object body) throws Exception {
        return mockMvc.perform(post(uri)
                .contentType(CONTENT_TYPE)
                .content(toJsonString(body))
                .accept(CONTENT_TYPE));
    }
}
