package com.lottoland.service;

import com.lottoland.application.service.GameService;
import com.lottoland.domain.api.Move;
import com.lottoland.domain.api.RoundDTO;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import org.junit.jupiter.api.*;
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
    private String sessionIdUser1;
    private String sessionIdUser2;

    HashMap<String, RoundsPerSingleSessionDTO> allRoundsForAllSessions;
    RoundsPerSingleSessionDTO roundsPerSingleSessionDTOUser1;
    RoundsPerSingleSessionDTO roundsPerSingleSessionDTOUser2;
    List<RoundDTO> allRoundsPerSingleSessionUser1;
    List<RoundDTO> allRoundsPerSingleSessionUser2;

    RoundDTO roundDTOFirstPlayerWinnerSessionUser1;
    RoundDTO roundDTOSecondPlayerWinnerSessionUser1;
    RoundDTO roundDTOSecondDrawSessionUser1;

    RoundDTO roundDTOFirstPlayerWinnerSessionUser2;
    RoundDTO roundDTOSecondPlayerWinnerSessionUser2;
    RoundDTO roundDTOSecondDrawSessionUser2;
    private static final int ROUND_NUMBER_PER_SINGLE_SESSION = 3;

    @BeforeEach
    void setUp(){

        sessionIdUser1 = "B48CB141D17B7E659E874022F5E000B5";
        sessionIdUser2 = "C48CB141D17B7E659E874022F5E000B6";

        roundDTOFirstPlayerWinnerSessionUser1 = new RoundDTO();
        roundDTOFirstPlayerWinnerSessionUser1.setFirstPlayerMove(Move.PAPER.getValue());
        roundDTOFirstPlayerWinnerSessionUser1.setRoundResult(RoundDTO.FIRST_PLAYER);

        roundDTOSecondPlayerWinnerSessionUser1 = new RoundDTO();
        roundDTOSecondPlayerWinnerSessionUser1.setFirstPlayerMove(Move.SCISSORS.getValue());
        roundDTOSecondPlayerWinnerSessionUser1.setRoundResult(RoundDTO.SECOND_PLAYER);

        roundDTOSecondDrawSessionUser1 = new RoundDTO();
        roundDTOSecondDrawSessionUser1.setFirstPlayerMove(Move.ROCK.getValue());
        roundDTOSecondDrawSessionUser1.setRoundResult(RoundDTO.DRAW);

        allRoundsPerSingleSessionUser1 =new ArrayList<>();
        allRoundsPerSingleSessionUser1.add(roundDTOFirstPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser1.add(roundDTOSecondPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser1.add(roundDTOSecondDrawSessionUser1);


        roundDTOFirstPlayerWinnerSessionUser2 = new RoundDTO();
        roundDTOFirstPlayerWinnerSessionUser2.setFirstPlayerMove(Move.PAPER.getValue());
        roundDTOFirstPlayerWinnerSessionUser2.setRoundResult(RoundDTO.FIRST_PLAYER);

        roundDTOSecondPlayerWinnerSessionUser2 = new RoundDTO();
        roundDTOSecondPlayerWinnerSessionUser2.setFirstPlayerMove(Move.SCISSORS.getValue());
        roundDTOSecondPlayerWinnerSessionUser2.setRoundResult(RoundDTO.SECOND_PLAYER);

        roundDTOSecondDrawSessionUser2 = new RoundDTO();
        roundDTOSecondDrawSessionUser2.setFirstPlayerMove(Move.ROCK.getValue());
        roundDTOSecondDrawSessionUser2.setRoundResult(RoundDTO.DRAW);

        allRoundsPerSingleSessionUser2 =new ArrayList<>();
        allRoundsPerSingleSessionUser2.add(roundDTOFirstPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser2.add(roundDTOSecondPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser2.add(roundDTOSecondDrawSessionUser1);

        roundsPerSingleSessionDTOUser1 = new RoundsPerSingleSessionDTO();
        roundsPerSingleSessionDTOUser1.setRoundNumbersPerSingleSession(ROUND_NUMBER_PER_SINGLE_SESSION);
        roundsPerSingleSessionDTOUser1.setAllRoundsPerSingleSession(allRoundsPerSingleSessionUser1);

        roundsPerSingleSessionDTOUser2 = new RoundsPerSingleSessionDTO();
        roundsPerSingleSessionDTOUser2.setRoundNumbersPerSingleSession(ROUND_NUMBER_PER_SINGLE_SESSION);
        roundsPerSingleSessionDTOUser2.setAllRoundsPerSingleSession(allRoundsPerSingleSessionUser2);

        allRoundsForAllSessions = new HashMap<>();
        allRoundsForAllSessions.put(sessionIdUser1, roundsPerSingleSessionDTOUser1);
        allRoundsForAllSessions.put(sessionIdUser2,roundsPerSingleSessionDTOUser2);

    }

    @Test
    @Order(1)
    @DisplayName("Play And Get All Rounds Result Per Single Session")
    void playAndGetAllRoundsDetailsPerSingleSessionTest() {

        RoundsPerSingleSessionDTO  roundsPerSingleSessionDTOSession  = gameService.playAndGetAllRoundsDetailsPerSingleSession(sessionIdUser1);

        int actualRoundNumbersPerSingleSession = roundsPerSingleSessionDTOSession.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMove = roundsPerSingleSessionDTOSession.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMove = roundsPerSingleSessionDTOSession.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResult = roundsPerSingleSessionDTOSession.getAllRoundsPerSingleSession().get(0).getRoundResult();

        boolean expectedFirstPlayerMove = validateOnPlayerMove(actualFirstPlayerMove);
        String expectedSecondPlayerMove = Move.ROCK.getValue();
        String expectedRoundResultAfterMoving = getExpectedRoundResultAfterMoving(actualFirstPlayerMove);


        Assertions.assertNotNull(roundsPerSingleSessionDTOSession,"The Round shouldn't be null");
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"Then Round number should be 1");
        Assertions.assertTrue(expectedFirstPlayerMove,"The random first play move should be value of [Paper, Scissors, Rock]");
        Assertions.assertEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 1, Player 2, Draw]");

    }

    /*@Test
    @DisplayName("Play And Get All Rounds Result Per Single Session Negative Case")
    void playAndGetAllRoundsDetailsPerSingleSessionTestFailed() {

        RoundsPerSingleSessionDTO  roundsPerSingleSessionDTOSession  = gameService.playAndGetAllRoundsDetailsPerSingleSession(null);

        Assertions.assertNotNull(roundsPerSingleSessionDTOSession,"The Round shouldn't be null");

    }

     */
    @Test
    @Order(3)
    @DisplayName("Restart Game Per Single Session User, Positive Case")
    void restartGameTestSuccess() {

        playAndGetAllRoundsDetailsPerSingleSessionTest();

        RoundsPerSingleSessionDTO acualRoundsPerSingleSessionDTO = gameService.restartGame(sessionIdUser1);

        boolean actualRoundIsRestarted = acualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).isRestarted();

        Assertions.assertTrue(actualRoundIsRestarted,"The Round shouldn be restarted");
    }

    @Test
    @Order(1)
    @DisplayName("Restart Game Per Single Session User, Negative Case")
    void restartGameTestFailed() {

        RoundsPerSingleSessionDTO acualRoundsPerSingleSessionDTO = gameService.restartGame(sessionIdUser1);

        int actualRoundNumbersPerSingleSession = acualRoundsPerSingleSessionDTO.getRoundNumbersPerSingleSession();

        Assertions.assertEquals( 0, actualRoundNumbersPerSingleSession,"The single user session should has rounds");
    }

    @Test
    @Order(4)
    @DisplayName("Play 1 automatic move")
    void getFirstPlayerRandomMoveTest() {

        String actualPlayerRandomMove = gameService.getFirstPlayerRandomMove();
        boolean expectedResult = false;

        if(actualPlayerRandomMove.equals(Move.PAPER.getValue()) || actualPlayerRandomMove.equals(Move.SCISSORS.getValue()) ||
                actualPlayerRandomMove.equals(Move.ROCK.getValue())){
            expectedResult = true;
        }

        Assertions.assertTrue(expectedResult,"The random play move should be value of [Paper, Scissors, Rock]");
    }

    @Test
    @Order(5)
    @DisplayName("Getting All Rounds Details Per Single User Session Test, Positive Case")
    void getAllRoundsDetailsPerSingleSessionTestSuccess() {

        RoundsPerSingleSessionDTO actualRoundsPerSingleSessionDTO = gameService.getAllRoundsDetailsPerSingleSession(roundDTOSecondPlayerWinnerSessionUser1, sessionIdUser1);

        int actualRoundNumbersPerSingleSession = actualRoundsPerSingleSessionDTO.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResult = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getRoundResult();

        String expectedFirstPlayerMove = Move.SCISSORS.getValue();
        String expectedSecondPlayerMove = Move.ROCK.getValue();
        String expectedRoundResultAfterMoving = RoundDTO.SECOND_PLAYER;

        Assertions.assertNotNull(actualRoundsPerSingleSessionDTO,"The Round shouldn't be null");
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"Then Round number should be 2");

        Assertions.assertEquals(expectedFirstPlayerMove, actualFirstPlayerMove,"The random first play move should be value of [Scissors]");
        Assertions.assertEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 2]");

    }

    @Test
    @Order(6)
    @DisplayName("Getting All Rounds Details Per Single User Session Test, Negative Case")
    void getAllRoundsDetailsPerSingleSessionTestFailed() {

        RoundsPerSingleSessionDTO actualRoundsPerSingleSessionDTO = gameService.getAllRoundsDetailsPerSingleSession(roundDTOSecondPlayerWinnerSessionUser1, sessionIdUser1);

        int actualRoundNumbersPerSingleSession = actualRoundsPerSingleSessionDTO.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResult = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getRoundResult();

        String expectedFirstPlayerMove = Move.PAPER.getValue();
        String expectedSecondPlayerMove = Move.SCISSORS.getValue();
        String expectedRoundResultAfterMoving = RoundDTO.FIRST_PLAYER;

        Assertions.assertNotNull(actualRoundsPerSingleSessionDTO,"The Round shouldn't be null");
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"Then Round number should be 2");

        Assertions.assertNotEquals(expectedFirstPlayerMove, actualFirstPlayerMove,"The random first play move should be value of [Scissors]");
        Assertions.assertNotEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertNotEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 2]");

    }

    private boolean validateOnPlayerMove(String playerMove){

        if(playerMove.equals(Move.PAPER.getValue()) || playerMove.equals(Move.SCISSORS.getValue()) ||
                playerMove.equals(Move.ROCK.getValue())){
            return true;
        }

        return false;
    }

    private String getExpectedRoundResultAfterMoving(String firstPlayerRandomMove){

        if(firstPlayerRandomMove.equals(Move.PAPER.getValue())){
            return RoundDTO.FIRST_PLAYER;
        }else if(firstPlayerRandomMove.equals(Move.SCISSORS.getValue())){
            return RoundDTO.SECOND_PLAYER;
        }

        return RoundDTO.DRAW;
    }
}
