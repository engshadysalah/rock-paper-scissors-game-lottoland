package com.lottoland.application.service;

import com.lottoland.domain.api.Move;
import com.lottoland.domain.api.RoundDTO;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class GameService {

    // This map holds session id for each user and his all game rounds and the game rounds that restarted.
    HashMap<String, RoundsPerSingleSessionDTO> allRoundsForAllSessions = new HashMap<>();

    /**
     * Execution: This service will be executed when a player click on "Play Round" button that will play an automatic round
     * The scenario that implemented: There will be 2 kinds of players, one should always choose randomly, the other should always choose rock.
     * Returns an RoundsPerSingleSessionDTO object that hold the following data for specific user session
     * based on the @parameter sessionId that comes from the client side:
     * ◦ Total rounds number
     * ◦ the rounds played, with 3 columns:
     * ◦   what 1st player chose,
     * ◦   what second chose,
     * •   and the result of the round (that could be player 1 wins, player 2 wins or draw)
     * @return   RoundsPerSingleSessionDTO object that holds all of the above data
     */
    public RoundsPerSingleSessionDTO playAndGetAllRoundsDetailsPerSingleSession(String sessionId) {

        String firstPlayerRandomMove = getFirstPlayerRandomMove();

        RoundDTO currentRoundDTO = new RoundDTO();
        currentRoundDTO.setFirstPlayerMove(firstPlayerRandomMove);
        currentRoundDTO.setRoundResult(getRoundResultAfterMoving(firstPlayerRandomMove));

        return getAllRoundsDetailsPerSingleSession(currentRoundDTO, sessionId);
    }

    /**
     * Execution: This service will be executed when a player click on "Restart" button that will restart the game per single session user.
     * The scenario that implemented: getting the RoundsPerSingleSessionDTO object per single session user that hold
     * ◦ roundNumbersPerSingleSession
     * ◦ List<RoundDTO> allRoundsPerSingleSession that holds all the game rounds details that not started per single user session.
     * Then update the isPresent for each round to be restarted.
     */
    public void restartGame(String sessionId) {

        Optional <RoundsPerSingleSessionDTO> allRoundsPerSingleSession = Optional.ofNullable(allRoundsForAllSessions.get(sessionId));

        if(allRoundsPerSingleSession.isPresent()){
            RoundsPerSingleSessionDTO roundsPerSingleSessionDTO = allRoundsPerSingleSession.get();

            for (RoundDTO currentRound : roundsPerSingleSessionDTO.getAllRoundsPerSingleSession()) {
                currentRound.setRestarted(true);
            }

        }
    }

    /**
    * Returns an HashMap object that hold
    * ◦ Total rounds played
    * ◦ Total Wins for first players
    * ◦ Total Wins for second players
    * ◦ Total draws
    * • These totals should consider all the rounds of all the games played by all users.
    *   (even if we clicked in "Restart button", these games should be considered as well)
    * @explaination of the code: allRoundsForAllSessions hashmap has RoundsPerSingleSessionDTO list of for each user session
    * Getting all the rounds of all the user sessions List<List<RoundDTO>> by iterating on allRoundsForAllSessions
    * then filtering on List<List<RoundDTO>> to get the counter for totalWinsForFirstPlayers, totalWinsForSecondPlayers,
    * totalDraws, and totalRoundsPlayed then adding them into getAllRoundsResultsForAllSessions map.
    * @return   getAllRoundsResultsForAllSessions map
    */
    public HashMap<String, AtomicInteger> getAllRoundsResultForAllSessions() {

        HashMap<String, AtomicInteger> getAllRoundsResultsForAllSessions = new HashMap<>();

        AtomicInteger totalWinsForFirstPlayers = new AtomicInteger();
        AtomicInteger totalWinsForSecondPlayers = new AtomicInteger();
        AtomicInteger totalDraws = new AtomicInteger();
        AtomicInteger totalRoundsPlayed = new AtomicInteger();

        allRoundsForAllSessions.entrySet()
                .stream()
                .map(entry->entry.getValue().getAllRoundsPerSingleSession())
                .toList()
                  .forEach(roundListPerSession -> roundListPerSession
                        .forEach(singleRound->
                                {
                                    if (singleRound.getRoundResult().equals(RoundDTO.FIRST_PLAYER)) {
                                        totalWinsForFirstPlayers.getAndIncrement();
                                    } else if (singleRound.getRoundResult().equals(RoundDTO.SECOND_PLAYER)) {
                                        totalWinsForSecondPlayers.getAndIncrement();
                                    }else {
                                        totalDraws.getAndIncrement();
                                    }
                                    totalRoundsPlayed.getAndIncrement();
                                })

                     );

        getAllRoundsResultsForAllSessions.put(RoundDTO.TOTAL_ROUNDS_PLAYED, totalRoundsPlayed);
        getAllRoundsResultsForAllSessions.put(RoundDTO.FIRST_PLAYER, totalWinsForFirstPlayers);
        getAllRoundsResultsForAllSessions.put(RoundDTO.SECOND_PLAYER, totalWinsForSecondPlayers);
        getAllRoundsResultsForAllSessions.put(RoundDTO.DRAW, totalDraws);

        return getAllRoundsResultsForAllSessions;
    }

    /**
     * The scenario that implemented: always choose random play for the First player [Computer as a player], the other should always choose rock.
     * @return a String that refer to the random play
     */
    public String getFirstPlayerRandomMove() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        return  Move.values()[randomNumber].getValue();
    }

    /**
     * @return an RoundsPerSingleSessionDTO object that hold
     * ◦ roundNumbersPerSingleSession
     * ◦ List<RoundDTO> allRoundsPerSingleSession that holds all the game rounds details that not started per single user session.
     * First step is getting the allRoundsPerSingleSession of specific user by his sessionId from the allRoundsForAllSessions map, but
     * in case not founded in the map then, it will be added into the map at the end.
     */
    public RoundsPerSingleSessionDTO getAllRoundsDetailsPerSingleSession(RoundDTO currentRoundDTO, String sessionId) {

        RoundsPerSingleSessionDTO roundsPerSingleSessionDTO = new RoundsPerSingleSessionDTO();

        Optional <RoundsPerSingleSessionDTO> allRoundsPerSingleSession = Optional.ofNullable(allRoundsForAllSessions.get(sessionId));

        if(allRoundsPerSingleSession.isPresent()){
            roundsPerSingleSessionDTO = allRoundsPerSingleSession.get();
        }

        calculateRoundNumbersPerSingleSession(roundsPerSingleSessionDTO.getAllRoundsPerSingleSession(), roundsPerSingleSessionDTO);
 
        roundsPerSingleSessionDTO.getAllRoundsPerSingleSession().add(currentRoundDTO);

        allRoundsForAllSessions.put(sessionId, roundsPerSingleSessionDTO);

        return getAllNotRestartedRoundsDetailsPerSingleSession(roundsPerSingleSessionDTO, sessionId);
    }

    /**
     * The scenario that implemented: Getting the round result based on There will be 2 kinds of players, one should always choose randomly,
     * and the other should always choose rock.
     * @return [Paper, Scissors, or Rock] based on the [Rock-paper-scissors] game rules as the following:
     * ◦ If [Paper, Rock] then who played 1 winning
     * ◦ If [Scissors, Rock] then who played 2 winning
     * ◦ If [Rock, Rock] then Draw
     */
    private String getRoundResultAfterMoving(String firstPlayerRandomMove){

        if(firstPlayerRandomMove.equals(Move.PAPER.getValue())){
            return RoundDTO.FIRST_PLAYER;

        }else if(firstPlayerRandomMove.equals(Move.SCISSORS.getValue())){
            return RoundDTO.SECOND_PLAYER;
        }

        return RoundDTO.DRAW;
    }

    /**
     * This method used to calculate the game rounds numbers for a single user session.
     */
    private void calculateRoundNumbersPerSingleSession(List<RoundDTO> allRoundsPerSingleSession, RoundsPerSingleSessionDTO roundsPerSingleSessionDTO){

        Optional<List <RoundDTO>>  allRoundsPerSingleSessionOptional = Optional.ofNullable(allRoundsPerSingleSession);

        if(allRoundsPerSingleSessionOptional.isPresent()){
            roundsPerSingleSessionDTO.setRoundNumbersPerSingleSession(roundsPerSingleSessionDTO.getRoundNumbersPerSingleSession() + 1);
        }else {
            roundsPerSingleSessionDTO.setAllRoundsPerSingleSession(new ArrayList<>());
            roundsPerSingleSessionDTO.setRoundNumbersPerSingleSession(1);
        }
    }

    /**
     * This method is used to filter the game rounds per single session user to get just only the rounds that not restarted.
     */
    public RoundsPerSingleSessionDTO getAllNotRestartedRoundsDetailsPerSingleSession(RoundsPerSingleSessionDTO roundsPerSingleSessionDTO, String sessionId){

        List<RoundDTO> notRestartedRounds = roundsPerSingleSessionDTO.getAllRoundsPerSingleSession()
                .stream()
                .filter(value -> !value.isRestarted())
                .collect(Collectors.toList());

        HashMap<String, RoundsPerSingleSessionDTO> allNotRestartedRoundsForAllSessions = new HashMap<>();

        RoundsPerSingleSessionDTO roundsNotRestartedPerSingleSessionDTO = new RoundsPerSingleSessionDTO();
        roundsNotRestartedPerSingleSessionDTO.setAllRoundsPerSingleSession(notRestartedRounds);
        roundsNotRestartedPerSingleSessionDTO.setRoundNumbersPerSingleSession(notRestartedRounds.size());

        allNotRestartedRoundsForAllSessions.put(sessionId, roundsNotRestartedPerSingleSessionDTO);

        return roundsNotRestartedPerSingleSessionDTO;

    }

}
