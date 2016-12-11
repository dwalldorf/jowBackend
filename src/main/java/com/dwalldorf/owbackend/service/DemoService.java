package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.DemoRepository;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private DemoRepository demoRepository;

    @Inject
    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public Demo save(Demo demo) {
        return demoRepository.save(demo);
    }

    public List<Demo> findByUser(final User user) {
        return demoRepository.findByUserId(user.getId());
    }
}
