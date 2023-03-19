package com.lottoland.service;

import com.lottoland.application.service.GameService;
import com.lottoland.domain.api.Move;
import com.lottoland.domain.api.Round;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class GameServiceTests {

    @Autowired
    private GameService gameService;

    private String sessionIdUser1;
    private String sessionIdUser2;

    HashMap<String, RoundsPerSingleSessionDTO> allRoundsForAllSessions;
    RoundsPerSingleSessionDTO roundsPerSingleSessionDTOUser1;
    RoundsPerSingleSessionDTO roundsPerSingleSessionDTOUser2;
    List<Round> allRoundsPerSingleSessionUser1;
    List<Round> allRoundsPerSingleSessionUser2;
    Round roundFirstPlayerWinnerSessionUser1;
    Round roundSecondPlayerWinnerSessionUser1;
    Round roundSecondDrawSessionUser1;
    Round roundFirstPlayerWinnerSessionUser2;
    Round roundSecondPlayerWinnerSessionUser2;
    Round roundSecondDrawSessionUser2;
    private static final int ROUND_NUMBER_PER_SINGLE_SESSION = 3;
    HashMap<String, AtomicInteger> getAllRoundsResultsForAllSessions;


    @BeforeEach
    void setUp(){

        sessionIdUser1 = "B48CB141D17B7E659E874022F5E000B5";
        sessionIdUser2 = "C48CB141D17B7E659E874022F5E000B6";

        roundFirstPlayerWinnerSessionUser1 = new Round();
        roundFirstPlayerWinnerSessionUser1.setFirstPlayerMove(Move.PAPER.getValue());
        roundFirstPlayerWinnerSessionUser1.setRoundResult(Round.FIRST_PLAYER);

        roundSecondPlayerWinnerSessionUser1 = new Round();
        roundSecondPlayerWinnerSessionUser1.setFirstPlayerMove(Move.SCISSORS.getValue());
        roundSecondPlayerWinnerSessionUser1.setRoundResult(Round.SECOND_PLAYER);

        roundSecondDrawSessionUser1 = new Round();
        roundSecondDrawSessionUser1.setFirstPlayerMove(Move.ROCK.getValue());
        roundSecondDrawSessionUser1.setRoundResult(Round.DRAW);
        roundSecondDrawSessionUser1.setRestarted(true);

        allRoundsPerSingleSessionUser1 =new ArrayList<>();
        allRoundsPerSingleSessionUser1.add(roundFirstPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser1.add(roundSecondPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser1.add(roundSecondDrawSessionUser1);


        roundFirstPlayerWinnerSessionUser2 = new Round();
        roundFirstPlayerWinnerSessionUser2.setFirstPlayerMove(Move.PAPER.getValue());
        roundFirstPlayerWinnerSessionUser2.setRoundResult(Round.FIRST_PLAYER);

        roundSecondPlayerWinnerSessionUser2 = new Round();
        roundSecondPlayerWinnerSessionUser2.setFirstPlayerMove(Move.SCISSORS.getValue());
        roundSecondPlayerWinnerSessionUser2.setRoundResult(Round.SECOND_PLAYER);

        roundSecondDrawSessionUser2 = new Round();
        roundSecondDrawSessionUser2.setFirstPlayerMove(Move.ROCK.getValue());
        roundSecondDrawSessionUser2.setRoundResult(Round.DRAW);

        allRoundsPerSingleSessionUser2 =new ArrayList<>();
        allRoundsPerSingleSessionUser2.add(roundFirstPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser2.add(roundSecondPlayerWinnerSessionUser1);
        allRoundsPerSingleSessionUser2.add(roundSecondDrawSessionUser1);

        roundsPerSingleSessionDTOUser1 = new RoundsPerSingleSessionDTO();
        roundsPerSingleSessionDTOUser1.setRoundNumbersPerSingleSession(ROUND_NUMBER_PER_SINGLE_SESSION);
        roundsPerSingleSessionDTOUser1.setAllRoundsPerSingleSession(allRoundsPerSingleSessionUser1);

        roundsPerSingleSessionDTOUser2 = new RoundsPerSingleSessionDTO();
        roundsPerSingleSessionDTOUser2.setRoundNumbersPerSingleSession(ROUND_NUMBER_PER_SINGLE_SESSION);
        roundsPerSingleSessionDTOUser2.setAllRoundsPerSingleSession(allRoundsPerSingleSessionUser2);

        allRoundsForAllSessions = new HashMap<>();
        allRoundsForAllSessions.put(sessionIdUser1, roundsPerSingleSessionDTOUser1);
        allRoundsForAllSessions.put(sessionIdUser2,roundsPerSingleSessionDTOUser2);

        getAllRoundsResultsForAllSessions = new HashMap<>();
        getAllRoundsResultsForAllSessions.put(Round.TOTAL_ROUNDS_PLAYED, new AtomicInteger(3));
        getAllRoundsResultsForAllSessions.put(Round.FIRST_PLAYER, new AtomicInteger(1));
        getAllRoundsResultsForAllSessions.put(Round.SECOND_PLAYER, new AtomicInteger(1));
        getAllRoundsResultsForAllSessions.put(Round.DRAW, new AtomicInteger(1));

    }


    @Test
    @DisplayName("Round Result After First Player Paper Move")
    void getRoundWinnerAfterFirstPlayerPaperMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.PAPER.getValue());
        String expectedRoundWinner = Round.FIRST_PLAYER;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The winner should be Player 1]");
    }

    @Test
    @DisplayName("Round Result After First Player Scissors Move")
    void getRoundWinnerAfterFirstPlayerScissorsMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.SCISSORS.getValue());
        String expectedRoundWinner = Round.SECOND_PLAYER;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The winner should be Player 2]");
    }

    @Test
    @DisplayName("Round Result After First Player Rock Move")
    void getRoundWinnerAfterFirstPlayerRockMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.ROCK.getValue());
        String expectedRoundWinner = Round.DRAW;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The round result should be [Draw]]");
    }

    @Test
    @DisplayName("Player 1 automatic move")
    void getFirstPlayerRandomMoveTest() {

        String actualPlayerRandomMove = gameService.getFirstPlayerRandomMove();

        boolean expectedResult = validateOnPlayerMove(actualPlayerRandomMove);

        Assertions.assertTrue(expectedResult,"The random play move should be value of [Paper, Scissors, Rock]");
    }

    @Test
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
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"The Round number should be 1");
        Assertions.assertTrue(expectedFirstPlayerMove,"The random first play move should be value of [Paper, Scissors, Rock]");
        Assertions.assertEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 1, Player 2, Draw]");

    }

    @Test
    @DisplayName("Getting All Rounds Details Per Single User Session Test, Positive Case")
    void getAllRoundsDetailsPerSingleSessionTestSuccess() {

        RoundsPerSingleSessionDTO actualRoundsPerSingleSessionDTOU = gameService.getAllRoundsDetailsPerSingleSession(roundFirstPlayerWinnerSessionUser1, sessionIdUser1);

        int actualRoundNumbersPerSingleSession = actualRoundsPerSingleSessionDTOU.getRoundNumbersPerSingleSession();
        String actualFirstPlayerMove = actualRoundsPerSingleSessionDTOU.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMove = actualRoundsPerSingleSessionDTOU.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();

        boolean expectedFirstPlayerMove = validateOnPlayerMove(actualFirstPlayerMove);
        String expectedSecondPlayerMove = Move.ROCK.getValue();


        Assertions.assertTrue(expectedFirstPlayerMove,"The random play move should be value of [Paper, Scissors, Rock]");
        Assertions.assertEquals(expectedSecondPlayerMove, actualSecondPlayerMove, "The random play move should be value of [Rock]");
        Assertions.assertNotEquals(0, actualRoundNumbersPerSingleSession,"The Round number should be 3");

    }

    @Test
    @DisplayName("Getting All Rounds Details Per Single User Session Test, Negative Case")
    void getAllRoundsDetailsPerSingleSessionTestFailed() {

        RoundsPerSingleSessionDTO actualRoundsPerSingleSessionDTO = gameService.getAllRoundsDetailsPerSingleSession(roundSecondPlayerWinnerSessionUser1, sessionIdUser2);

        int actualRoundNumbersPerSingleSession = actualRoundsPerSingleSessionDTO.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMove = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResult = actualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getRoundResult();

        String expectedFirstPlayerMove = Move.PAPER.getValue();
        String expectedSecondPlayerMove = Move.SCISSORS.getValue();
        String expectedRoundResultAfterMoving = Round.FIRST_PLAYER;

        Assertions.assertNotNull(actualRoundsPerSingleSessionDTO,"The Round shouldn't be null");
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"The Round number should be 1");

        Assertions.assertNotEquals(expectedFirstPlayerMove, actualFirstPlayerMove,"The random first play move should be value of [Scissors]");
        Assertions.assertNotEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertNotEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 2]");

    }

    @Test
    @DisplayName("Calculate the round numbers per single user session Test")
    void calculateRoundNumbersPerSingleSessionTest() {

        int actualRoundNumbersPerSingleSession = gameService.calculateRoundNumbersPerSingleSession(allRoundsPerSingleSessionUser1, roundsPerSingleSessionDTOUser1);

        Assertions.assertEquals(4, actualRoundNumbersPerSingleSession,"The Rounds number should be 4");
    }

    @Test
    @DisplayName("Get All Not Restarted Rounds Details per single user session Test")
    void getAllNotRestartedRoundsDetailsPerSingleSessionTest() {

        int expectedNotRestartedRoundNumbersPerSingleSession = roundsPerSingleSessionDTOUser1.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMoveRound1 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMoveRound1 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResultRound1 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(0).getRoundResult();
        boolean actualIsRestartedRound1 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(0).isRestarted();

        String actualFirstPlayerMoveRound2 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(1).getFirstPlayerMove();
        String actualSecondPlayerMoveRound2 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(1).getSecondPlayerMove();
        String actualRoundResultRound2 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(1).getRoundResult();
        boolean actualIsRestartedRound2 = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(1).isRestarted();

        String expectedFirstPlayerMoveRound1 = Move.PAPER.getValue();
        String expectedSecondPlayerMoveRound1 = Move.ROCK.getValue();
        String expectedRoundResultAfterMovingRound1 = Round.FIRST_PLAYER;

        String expectedFirstPlayerMoveRound2 = Move.SCISSORS.getValue();
        String expectedSecondPlayerMoveRound2 = Move.ROCK.getValue();
        String expectedRoundResultAfterMovingRound2 = Round.SECOND_PLAYER;

        Assertions.assertEquals(3, expectedNotRestartedRoundNumbersPerSingleSession,"The Rounds number should be 3");

        Assertions.assertEquals(expectedFirstPlayerMoveRound1, actualFirstPlayerMoveRound1, "[Round1] The first play move should be value of [Paper]");
        Assertions.assertEquals(expectedSecondPlayerMoveRound1, actualSecondPlayerMoveRound1,"[Round1] The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMovingRound1, actualRoundResultRound1,"[Round1] The Round Result should be value of [Player 1]");
        Assertions.assertFalse(actualIsRestartedRound1,"[Round1] The Round isRestarted should be value of [False]");

        Assertions.assertEquals(expectedFirstPlayerMoveRound2, actualFirstPlayerMoveRound2, "[Round2] The first play move should be value of [Paper]");
        Assertions.assertEquals(expectedSecondPlayerMoveRound2, actualSecondPlayerMoveRound2,"[Round2] The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMovingRound2, actualRoundResultRound2,"[Round2] The Round Result should be value of [Player 1]");
        Assertions.assertFalse(actualIsRestartedRound2,"[Round2] The Round isRestarted should be value of [False]");

    }

    @Test
    @DisplayName("Restart Game Per Single Session User")
    void restartGameTest() {

       boolean actualRoundIsRestarted = roundsPerSingleSessionDTOUser1.getAllRoundsPerSingleSession().get(2).isRestarted();

       Assertions.assertTrue(actualRoundIsRestarted,"The Round should be restarted");
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
            return Round.FIRST_PLAYER;
        }else if(firstPlayerRandomMove.equals(Move.SCISSORS.getValue())){
            return Round.SECOND_PLAYER;
        }

        return Round.DRAW;
    }
}
