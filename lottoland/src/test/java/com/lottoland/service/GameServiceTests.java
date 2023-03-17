package com.lottoland.service;

import com.lottoland.application.service.GameService;
import com.lottoland.domain.api.Move;
import com.lottoland.domain.api.RoundDTO;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
class GameServiceTests {

    @Autowired
    private GameService gameService;

    private RoundDTO roundDTO;
    private String sessionId;

    @BeforeEach
    void setUp(){
        sessionId = "B48CB141D17B7E659E874022F5E000B5";
    }

    @Test
    @DisplayName("Play And Get All Rounds Result Per Single Session")
    void playAndGetAllRoundsDetailsPerSingleSessionTest() {

        HashMap<String, RoundsPerSingleSessionDTO> expectedResult = new HashMap<>();

        RoundDTO currentRoundDTO = new RoundDTO();
        currentRoundDTO.setFirstPlayerMove(Move.PAPER.getValue());
        currentRoundDTO.setRoundResult(RoundDTO.FIRST_PLAYER);

        List<RoundDTO> allRoundsPerSingleSession = new ArrayList<>();
        allRoundsPerSingleSession.add(currentRoundDTO);

        RoundsPerSingleSessionDTO roundsNotRestartedPerSingleSessionDTO = new RoundsPerSingleSessionDTO();
        roundsNotRestartedPerSingleSessionDTO.setAllRoundsPerSingleSession(allRoundsPerSingleSession);
        roundsNotRestartedPerSingleSessionDTO.setRoundNumbersPerSingleSession(allRoundsPerSingleSession.size());

        expectedResult.put(sessionId, roundsNotRestartedPerSingleSessionDTO);


        //HashMap<String, RoundsPerSingleSessionDTO> actualResult = gameService.playAndGetAllRoundsDetailsPerSingleSession(sessionId);


       // assertNotNull(1L, allRoundsPerSingleSession.get(0).getFirstPlayerMove(),"The account id should be 1" );

         //Assertions.assertEquals(actualResult.get(sessionId).getRoundNumbersPerSingleSession(), expectedResult.get(sessionId).getRoundNumbersPerSingleSession(),"The round numbers should be 1" );

       // Assertions.assertEquals(actualResult.get(sessionId).getAllRoundsPerSingleSession().get(0)., expectedResult.get(sessionId).getRoundNumbersPerSingleSession(),"The round numbers should be 1" );

    }

    @Test
    @DisplayName("Play And Get All Rounds Result Per Single Session")
    void restartGameTest() {

        gameService.restartGame(null);

    }
}
