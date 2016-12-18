package com.dwalldorf.owbackend.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.event.DemoAnalyzedEvent;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.repository.DemoRepository;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

public class DemoServiceTest extends BaseTest {

    @Mock
    private DemoRepository demoRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private DemoService demoService;

    @Override
    protected void afterSetup() {
        this.demoService = new DemoService(demoRepository, eventPublisher);
    }

    @Test
    public void testSave_PublishesSavedEvent() throws Exception {
        demoService.save(new Demo());
        verify(eventPublisher).publishEvent(any(DemoAnalyzedEvent.class));
    }
}