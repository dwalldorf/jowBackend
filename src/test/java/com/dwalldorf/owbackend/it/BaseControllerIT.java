package com.dwalldorf.owbackend.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.dwalldorf.owbackend.Application;
import com.dwalldorf.owbackend.repository.DemoFileRepository;
import com.dwalldorf.owbackend.repository.DemoRepository;
import com.dwalldorf.owbackend.repository.OverwatchUserScoreRepository;
import com.dwalldorf.owbackend.repository.OverwatchVerdictRepository;
import com.dwalldorf.owbackend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles({Application.PROFILE_INTEGRATION_TEST})
public abstract class BaseControllerIT {

    protected MockMvc mockMvc;

    @Inject
    private WebApplicationContext ctx;

    @Inject
    private ObjectMapper mapper;

    @MockBean
    protected DemoFileRepository demoFileRepositoryMock;

    @MockBean
    protected DemoRepository demoRepository;

    @MockBean
    protected OverwatchUserScoreRepository overwatchUserScoreRepository;

    @MockBean
    protected OverwatchVerdictRepository overwatchVerdictRepository;

    @MockBean
    protected UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        afterSetup();
    }

    protected void afterSetup() {
    }

    protected ResultActions doGet(final String uri) throws Exception {
        return prepareRequest(get(uri));
    }

    protected ResultActions doPost(final String uri, final Object body) throws Exception {
        return prepareRequest(post(uri), body);
    }

    protected ResultActions doPost(final String uri) throws Exception {
        return doPost(uri, null);
    }

    protected ResultActions doPut(final String uri, final Object body) throws Exception {
        return prepareRequest(put(uri), body);
    }

    private ResultActions prepareRequest(MockHttpServletRequestBuilder builder) throws Exception {
        return prepareRequest(builder, null);
    }

    private ResultActions prepareRequest(MockHttpServletRequestBuilder builder, Object body) throws Exception {
        builder.accept(MediaType.APPLICATION_JSON_UTF8)
               .contentType(MediaType.APPLICATION_JSON_UTF8);

        if (body != null) {
            builder.content(toJsonString(body));
        }

        return mockMvc.perform(builder)
                      .andDo(print());
    }

    private String toJsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
