package com.dwalldorf.owbackend;

import static org.mockito.Mockito.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

    protected JoinPoint createJoinPointMock() {
        JoinPoint joinPointMock = Mockito.mock(JoinPoint.class);
        Signature signatureMock = Mockito.mock(Signature.class);

        when(signatureMock.toString()).thenReturn("SomeBean#someMethod");
        when(joinPointMock.getSignature()).thenReturn(signatureMock);

        return joinPointMock;
    }
}
