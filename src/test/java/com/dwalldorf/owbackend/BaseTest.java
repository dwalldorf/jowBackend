package com.dwalldorf.owbackend;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.afterSetup();
    }

    protected void afterSetup() {
    }

    protected void mockLogger(Object target) {
        ReflectionTestUtils.setField(target, "logger", LoggerFactory.getLogger(target.getClass()));
    }
}
