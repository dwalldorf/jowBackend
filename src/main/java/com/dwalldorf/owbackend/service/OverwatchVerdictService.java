package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.OverwatchVerdictRepository;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class OverwatchVerdictService {

    @Inject
    private OverwatchVerdictRepository verdictRepository;

    public OverwatchVerdict save(OverwatchVerdict verdict) {
        return verdictRepository.save(verdict);
    }

    public List<OverwatchVerdict> findByUser(User user) {
        return verdictRepository.findByUserId(user.getId());
    }
}
