package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.event.user.UserPostedVerdictEvent;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.OverwatchVerdictRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OverwatchVerdictService {

    private final UserService userService;

    private final OverwatchVerdictRepository verdictRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Inject
    public OverwatchVerdictService(ApplicationEventPublisher eventPublisher, UserService userService, OverwatchVerdictRepository verdictRepository) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.verdictRepository = verdictRepository;
    }

    public OverwatchVerdict save(OverwatchVerdict verdict) throws LoginRequiredException {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new LoginRequiredException();
        }

        verdict.setUserId(currentUser.getId());
        verdict.setCreationDate(new Date());
        verdict = verdictRepository.save(verdict);

        eventPublisher.publishEvent(new UserPostedVerdictEvent(currentUser, verdict));
        return verdict;
    }

    public List<OverwatchVerdict> findByUser(User user) {
        return verdictRepository.findByUserId(user.getId());
    }

    public boolean hasVerdictsAfter(final Date date) {
        List<OverwatchVerdict> result = verdictRepository.findByCreationDateGreaterThanEqual(date);
        return (result.size() > 0);
    }
}
