package com.dwalldorf.owbackend.service;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.DemoMatchInfo;
import com.dwalldorf.owbackend.model.DemoPlayer;
import com.dwalldorf.owbackend.model.DemoPlayerStats;
import com.dwalldorf.owbackend.model.DemoRound;
import com.dwalldorf.owbackend.model.DemoRoundStats;
import com.dwalldorf.owbackend.model.DemoTeam;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsgoDemosManagerService {

    private static final Map<Integer, Map<String, Class>> map = new HashMap<>();

    static {
        map.put(0, Collections.singletonMap("id", String.class));
    }

    @Log
    private Logger logger;

    private final DemoService demoService;

    private final CsgoDemosManagerExcelService excelService;

    private final UserService userService;

    @Inject
    public CsgoDemosManagerService(DemoService demoService, CsgoDemosManagerExcelService excelService, UserService userService) {
        this.demoService = demoService;
        this.excelService = excelService;
        this.userService = userService;
    }

    public Demo processFile(MultipartFile file) throws InvalidInputException {
        Demo demo = null;
        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".xlsx")) {
            throw new InvalidInputException("Unsupported file: " + filename);
        }

        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook sheets = new XSSFWorkbook(inputStream);

            demo = demoService.findByMatchId(getMatchId(sheets));
            if (demo == null) {
                demo = new Demo()
                        .setUserId(userService.getCurrentUser().getId())
                        .setAnalyzed(true);
            }
            demo.setMatchInfo(getMatchInfo(sheets));

            demo = demoService.save(demo);
        } catch (IOException e) {
            logger.warn(appInfoMarker, e.getMessage(), e);
        }
        return demo;
    }

    private String getMatchId(XSSFWorkbook sheets) {
        XSSFSheet demoSheet = sheets.getSheetAt(0);
        return excelService.stringFromCell(demoSheet.getRow(1), 1);
    }

    private DemoMatchInfo getMatchInfo(XSSFWorkbook sheets) throws IOException {
        XSSFSheet demoSheet = sheets.getSheetAt(0);
        XSSFSheet playerSheet = sheets.getSheetAt(1);

        if (demoSheet.getLastRowNum() > 1) {
            throw new InvalidInputException("Uploaded excel file contains multiple demos. Only single demo exports supported.");
        }

        XSSFRow demoRow = demoSheet.getRow(1);
        Map<Integer, DemoTeam> teams = getTeams(playerSheet);

        return new DemoMatchInfo()
                .setMatchId(getMatchId(sheets))
                .setDate(excelService.dateFromCell(demoRow, 2))
                .setServerName(excelService.stringFromCell(demoRow, 6))
                .setTickRate(excelService.intFromCell(demoRow, 8))
                .setDuration(excelService.intFromCell(demoRow, 10))
                .setDemoType(excelService.stringFromCell(demoRow, 3))
                .setMap(excelService.stringFromCell(demoRow, 5))
                .setScoreTeam1(excelService.intFromCell(demoRow, 14))
                .setScoreTeam2(excelService.intFromCell(demoRow, 15))
                .setScoreHalftimeTeam1(excelService.intFromCell(demoRow, 16))
                .setScoreHalftimeTeam2(excelService.intFromCell(demoRow, 17))
                .setRounds(getRounds(sheets))
                .setTeam1(teams.get(1))
                .setTeam2(teams.get(2));
    }

    private Map<Integer, DemoTeam> getTeams(XSSFSheet playerSheet) {
        Map<Integer, DemoTeam> retVal = new HashMap<>();

        DemoTeam team1 = new DemoTeam().setTeamNumber(1);
        DemoTeam team2 = new DemoTeam().setTeamNumber(2);

        playerSheet.forEach(row -> {
            if (row.getRowNum() == 0) {
                return;
            }

            DemoPlayer player = new DemoPlayer();
            player.setName(excelService.stringFromCell(row, 0))
                  .setSteamId(excelService.stringFromCell(row, 1));

            DemoPlayerStats playerStats = new DemoPlayerStats()
                    .setKills(excelService.intFromCell(row, 4))
                    .setAssists(excelService.intFromCell(row, 5))
                    .setDeaths(excelService.intFromCell(row, 6))
                    .setKd(excelService.doubleFromCell(row, 7))
                    .setHeadshots(excelService.intFromCell(row, 8))
                    .setTeamKills(excelService.intFromCell(row, 10))
                    .setEntryKills(excelService.intFromCell(row, 11))
                    .setBombPlants(excelService.intFromCell(row, 12))
                    .setBombDefuses(excelService.intFromCell(row, 13))
                    .setMvps(excelService.intFromCell(row, 14))
                    .setKills1(excelService.intFromCell(row, 27))
                    .setKills2(excelService.intFromCell(row, 26))
                    .setKills3(excelService.intFromCell(row, 25))
                    .setKills4(excelService.intFromCell(row, 24))
                    .setKills5(excelService.intFromCell(row, 23))
                    .setVacRecords(excelService.intFromCell(row, 58))
                    .setOverwatchRecords(excelService.intFromCell(row, 59));

            player.setPlayerStats(playerStats);

            if (excelService.stringFromCell(row, 3).equals("Team 1")) {
                team1.addPlayer(player);
            } else {
                team2.addPlayer(player);
            }
        });

        retVal.put(1, team1);
        retVal.put(2, team2);
        return retVal;
    }

    private List<DemoRound> getRounds(XSSFWorkbook sheets) {
        List<DemoRound> rounds = new ArrayList<>();

        XSSFSheet roundsSheet = sheets.getSheetAt(2);
        roundsSheet.forEach(row -> {
            if (row.getRowNum() == 0) {
                return;
            }

            DemoRoundStats roundStats = new DemoRoundStats();
            roundStats.setKills(excelService.intFromCell(row, 9))
                      .setKills1(excelService.intFromCell(row, 10))
                      .setKills2(excelService.intFromCell(row, 11))
                      .setKills3(excelService.intFromCell(row, 12))
                      .setKills4(excelService.intFromCell(row, 13))
                      .setKills5(excelService.intFromCell(row, 14))
                      .setBombPlanted(excelService.boolFromIntCell(row, 21))
                      .setBombExploded(excelService.boolFromIntCell(row, 20))
                      .setBombDefused(excelService.boolFromIntCell(row, 22))
                      .setStartMoneyTeam1(excelService.intFromCell(row, 23))
                      .setStartmoneyTeam2(excelService.intFromCell(row, 24))
                      .setEquipmentValueTeam1(excelService.intFromCell(row, 25))
                      .setEquipmentValueTeam2(excelService.intFromCell(row, 26));

            DemoRound round = new DemoRound()
                    .setNr(excelService.intFromCell(row, 0))
                    .setDuration(excelService.longFromCell(row, 2))
                    .setRoundStats(roundStats);

            int winner = 0;
            if (excelService.stringFromCell(row, 3).equals("Team 1")) {
                winner = 1;
            } else {
                winner = 2;
            }
            round.setWinner(winner);

            rounds.add(round);
        });

        return rounds;
    }
}
