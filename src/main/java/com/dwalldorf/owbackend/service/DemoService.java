package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.event.DemoAnalyzedEvent;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.DemoRepository;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final DemoRepository demoRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Inject
    public DemoService(DemoRepository demoRepository, ApplicationEventPublisher eventPublisher) {
        this.demoRepository = demoRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Demo> findByUser(final User user) {
        return demoRepository.findByUserId(user.getId());
    }

    public Demo save(Demo demo) {
        demo = demoRepository.save(demo);
        eventPublisher.publishEvent(new DemoAnalyzedEvent(demo));

        return demo;
    }

    public Demo findByMatchId(final String matchId) {
        return demoRepository.findByMatchInfo_MatchId(matchId);
    }
}
