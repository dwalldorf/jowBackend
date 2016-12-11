package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.repository.OverwatchUserScoreRepository;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class OverwatchUserScoreServiceTest extends BaseTest {

    @Mock
    private MongoClient mongoClient;

    @Mock
    private OverwatchUserScoreRepository scoreRepository;

    @InjectMocks
    private OverwatchUserScoreService scoreService;

    @Override
    protected void afterSetup() {
        ReflectionTestUtils.setField(scoreService, "dbName", "");
    }

    @Test
    public void testGetUserScoresByPeriod() throws Exception {

    }

    @Test
    public void testReprocessUserScores() throws Exception {

    }

    @Test
    public void testGetLatestScore() throws Exception {
        ArrayList<OverwatchUserScore> overwatchUserScores = new ArrayList<>();
        overwatchUserScores.add(new OverwatchUserScore());

//        PageImpl<OverwatchUserScore> pageResultMock = (PageImpl<OverwatchUserScore>) mock(PageImpl.class);
//        ReflectionTestUtils.setField(pageResultMock,);
//        when(pageResultMock.getContent()).thenReturn(overwatchUserScores);

//
//        ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
//        verify(scoreRepository).findAll(pageRequestArgumentCaptor.capture());
    }
}