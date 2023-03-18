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

    HashMap<String, RoundsPerSingleSessionDTO> allRoundsForAllSessions = new HashMap<>();

    public RoundsPerSingleSessionDTO playAndGetAllRoundsDetailsPerSingleSession(String sessionId) {

        String firstPlayerRandomMove = getFirstPlayerRandomMove();

        RoundDTO currentRoundDTO = new RoundDTO();
        currentRoundDTO.setFirstPlayerMove(firstPlayerRandomMove);
        currentRoundDTO.setRoundResult(getRoundResultAfterMoving(firstPlayerRandomMove));

        return getAllRoundsDetailsPerSingleSession(currentRoundDTO, sessionId);
    }

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
    public HashMap<String, AtomicInteger> getAllRoundsForAllSessions() {

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

    private String getFirstPlayerRandomMove() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        return  Move.values()[randomNumber].getValue();
    }

    private RoundsPerSingleSessionDTO getAllRoundsDetailsPerSingleSession(RoundDTO currentRoundDTO, String sessionId) {

        RoundsPerSingleSessionDTO roundsPerSingleSessionDTO = new RoundsPerSingleSessionDTO();

        Optional <RoundsPerSingleSessionDTO> allRoundsPerSingleSession = Optional.ofNullable(allRoundsForAllSessions.get(sessionId));

        if(allRoundsPerSingleSession.isPresent()){
            roundsPerSingleSessionDTO = allRoundsPerSingleSession.get();
        }

        setRoundNumbersPerSingleSession(roundsPerSingleSessionDTO.getAllRoundsPerSingleSession(), roundsPerSingleSessionDTO);
 
        roundsPerSingleSessionDTO.getAllRoundsPerSingleSession().add(currentRoundDTO);

        allRoundsForAllSessions.put(sessionId, roundsPerSingleSessionDTO);

        return getAllNotRestartedRoundsDetailsPerSingleSession(roundsPerSingleSessionDTO, sessionId);
    }

    private String getRoundResultAfterMoving(String firstPlayerRandomMove){

        if(firstPlayerRandomMove.equals(Move.PAPER.getValue())){
            return RoundDTO.FIRST_PLAYER;

        }else if(firstPlayerRandomMove.equals(Move.SCISSORS.getValue())){
            return RoundDTO.SECOND_PLAYER;
        }

        return RoundDTO.DRAW;
    }

    private void setRoundNumbersPerSingleSession(List<RoundDTO> allRoundsPerSingleSession, RoundsPerSingleSessionDTO roundsPerSingleSessionDTO){

        Optional<List <RoundDTO>>  allRoundsPerSingleSessionOptional = Optional.ofNullable(allRoundsPerSingleSession);

        if(allRoundsPerSingleSessionOptional.isPresent()){
            roundsPerSingleSessionDTO.setRoundNumbersPerSingleSession(roundsPerSingleSessionDTO.getRoundNumbersPerSingleSession() + 1);
        }else {
            roundsPerSingleSessionDTO.setAllRoundsPerSingleSession(new ArrayList<>());
            roundsPerSingleSessionDTO.setRoundNumbersPerSingleSession(1);
        }
    }

    private RoundsPerSingleSessionDTO getAllNotRestartedRoundsDetailsPerSingleSession(RoundsPerSingleSessionDTO roundsPerSingleSessionDTO, String sessionId){

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
