package com.dwalldorf.owbackend.annotation;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.service.UserService;
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
    private UserService userService;

    @Test
    public void testCreateLogger_SetsDeclaringClass() throws Exception {
        loggerInjector.postProcessAfterInitialization(userService, userService.getClass().getSimpleName());

        Field logger = ReflectionUtils.findField(userService.getClass(), "logger");

        Assert.assertNotNull(logger);
        Assert.assertEquals(UserService.class, logger.getDeclaringClass());
    }
}