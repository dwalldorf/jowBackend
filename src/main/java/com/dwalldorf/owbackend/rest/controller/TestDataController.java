package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.Application;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.OverwatchVerdictRepository;
import com.dwalldorf.owbackend.service.UserService;
import com.dwalldorf.owbackend.stub.OverwatchVerdictStub;
import com.dwalldorf.owbackend.stub.UserStub;
import com.dwalldorf.owbackend.util.RandomUtil;
import javax.inject.Inject;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestDataController.URI)
@Profile(Application.PROFILE_DEV)
public class TestDataController {

    public static final String URI = "/_test_data";

    private final UserService userService;

    private final UserStub userStub;

    private final OverwatchVerdictStub verdictStub;

    private final OverwatchVerdictRepository verdictRepository;

    private final RandomUtil randomUtil;

    @Inject
    public TestDataController(
            UserService userService,
            UserStub userStub,
            OverwatchVerdictStub verdictStub,
            OverwatchVerdictRepository verdictRepository,
            RandomUtil randomUtil) {
        this.userService = userService;
        this.userStub = userStub;
        this.verdictStub = verdictStub;
        this.verdictRepository = verdictRepository;
        this.randomUtil = randomUtil;
    }

    @GetMapping("/users")
    public ResponseEntity<String> createTestUsers(
            @RequestParam("amount") Integer users,
            @RequestParam("verdicts") Integer verdicts
    ) {
        if (users <= 0 || users > 10000) {
            return new ResponseEntity<>(users + " is out of bounds", HttpStatus.BAD_REQUEST);
        }

        for (int createdUsers = 0; createdUsers < users; createdUsers++) {
            User currentUser = userStub.createUser().setId(null);
            currentUser.getUserProperties().setPassword(randomUtil.randomString(20));

            currentUser = userService.register(currentUser);

            if (verdicts != null && verdicts > 0) {
                int verdictsForUser = randomUtil.randomInt(10, verdicts);
                for (int createdUserVerdicts = 0; createdUserVerdicts < verdictsForUser; createdUserVerdicts++) {
                    OverwatchVerdict verdict = verdictStub.createVerdict();
                    verdict.setUserId(currentUser.getId())
                           .setCreationDate(verdict.getOverwatchDate());

                    verdictRepository.save(verdict);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
