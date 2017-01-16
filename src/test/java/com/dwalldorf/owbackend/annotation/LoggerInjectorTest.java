package com.dwalldorf.owbackend.annotation;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.event.user.UserLoginEventHandler;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.ReflectionUtils;

public class LoggerInjectorTest extends BaseTest {

    @InjectMocks
    private LoggerInjector loggerInjector;

    @Mock
    private UserLoginEventHandler loginEventHandler;

    @Test
    public void testCreateLogger_SetsDeclaringClass() throws Exception {
        Class<? extends UserLoginEventHandler> loginEventHandlerClass = loginEventHandler.getClass();
        loggerInjector.postProcessAfterInitialization(loginEventHandler, loginEventHandlerClass.getSimpleName());

        Field logger = ReflectionUtils.findField(loginEventHandlerClass, "logger");

        Assert.assertNotNull(logger);
        Assert.assertEquals(UserLoginEventHandler.class, logger.getDeclaringClass());
    }
}