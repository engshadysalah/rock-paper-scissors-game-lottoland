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
        roundDTOSecondDrawSessionUser1.setRestarted(true);

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
    @DisplayName("Restart Game Per Single Session User")
    void restartGameTest() {

        RoundsPerSingleSessionDTO acualRoundsPerSingleSessionDTO = gameService.restartGame(sessionIdUser1);

        boolean actualRoundIsRestarted = acualRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).isRestarted();

        Assertions.assertTrue(actualRoundIsRestarted,"The Round shouldn be restarted");
    }

    @Test
    @DisplayName("Play 1 automatic move")
    void getFirstPlayerRandomMoveTest() {

        String actualPlayerRandomMove = gameService.getFirstPlayerRandomMove();

        boolean expectedResult = validateOnPlayerMove(actualPlayerRandomMove);

        Assertions.assertTrue(expectedResult,"The random play move should be value of [Paper, Scissors, Rock]");
    }

    @Test
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
        Assertions.assertEquals(2, actualRoundNumbersPerSingleSession,"The Round number should be 2");

        Assertions.assertEquals(expectedFirstPlayerMove, actualFirstPlayerMove,"The random first play move should be value of [Scissors]");
        Assertions.assertEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 2]");

    }

    @Test
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
        Assertions.assertEquals(1, actualRoundNumbersPerSingleSession,"The Round number should be 1");

        Assertions.assertNotEquals(expectedFirstPlayerMove, actualFirstPlayerMove,"The random first play move should be value of [Scissors]");
        Assertions.assertNotEquals(expectedSecondPlayerMove, actualSecondPlayerMove,"The random second play move should be value of [Rock]");
        Assertions.assertNotEquals(expectedRoundResultAfterMoving, actualRoundResult,"The Round Result should be value of [Player 2]");

    }

    @Test
    @DisplayName("Calculate the round numbers per single user session Test")
    void calculateRoundNumbersPerSingleSessionTest() {

        gameService.calculateRoundNumbersPerSingleSession(allRoundsPerSingleSessionUser1, roundsPerSingleSessionDTOUser1);

        int actualRoundNumbersPerSingleSession = roundsPerSingleSessionDTOUser1.getRoundNumbersPerSingleSession();

        Assertions.assertEquals(4, actualRoundNumbersPerSingleSession,"The Rounds number should be 4");

    }

    @Test
    @DisplayName("Get All Not Restarted Rounds Details per single user session Test")
    void getAllNotRestartedRoundsDetailsPerSingleSessionTest() {

        RoundsPerSingleSessionDTO expectedRoundsPerSingleSessionDTO = gameService.getAllNotRestartedRoundsDetailsPerSingleSession(roundsPerSingleSessionDTOUser1, sessionIdUser1);

        int expectedNotRestartedRoundNumbersPerSingleSession = expectedRoundsPerSingleSessionDTO.getRoundNumbersPerSingleSession();

        String actualFirstPlayerMoveRound1 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getFirstPlayerMove();
        String actualSecondPlayerMoveRound1 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getSecondPlayerMove();
        String actualRoundResultRound1 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).getRoundResult();
        boolean actualIsRestartedRound1 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(0).isRestarted();

        String actualFirstPlayerMoveRound2 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(1).getFirstPlayerMove();
        String actualSecondPlayerMoveRound2 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(1).getSecondPlayerMove();
        String actualRoundResultRound2 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(1).getRoundResult();
        boolean actualIsRestartedRound2 = expectedRoundsPerSingleSessionDTO.getAllRoundsPerSingleSession().get(1).isRestarted();

        String expectedFirstPlayerMoveRound1 = Move.PAPER.getValue();
        String expectedSecondPlayerMoveRound1 = Move.ROCK.getValue();
        String expectedRoundResultAfterMovingRound1 = RoundDTO.FIRST_PLAYER;

        String expectedFirstPlayerMoveRound2 = Move.SCISSORS.getValue();
        String expectedSecondPlayerMoveRound2 = Move.ROCK.getValue();
        String expectedRoundResultAfterMovingRound2 = RoundDTO.SECOND_PLAYER;

        Assertions.assertEquals(2, expectedNotRestartedRoundNumbersPerSingleSession,"The Rounds number should be 2");

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
    @DisplayName("Round Result After First Player Paper Move")
    void getRoundWinnerAfterFirstPlayerPaperMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.PAPER.getValue());
        String expectedRoundWinner = RoundDTO.FIRST_PLAYER;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The winner should be Player 1]");
    }

    @Test
    @DisplayName("Round Result After First Player Scissors Move")
    void getRoundWinnerAfterFirstPlayerScissorsMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.SCISSORS.getValue());
        String expectedRoundWinner = RoundDTO.SECOND_PLAYER;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The winner should be Player 2]");
    }

    @Test
    @DisplayName("Round Result After First Player Rock Move")
    void getRoundWinnerAfterFirstPlayerRockMoveTest(){

        String actualRoundWinner = gameService.getRoundWinnerAfterFirstPlayerMove(Move.ROCK.getValue());
        String expectedRoundWinner = RoundDTO.DRAW;

        Assertions.assertEquals(expectedRoundWinner, actualRoundWinner, "The round result should be [Draw]]");
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
