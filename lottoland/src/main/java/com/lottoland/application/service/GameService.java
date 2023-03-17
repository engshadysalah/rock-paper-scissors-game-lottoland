package com.lottoland.application.service;

import com.lottoland.domain.api.Move;
import com.lottoland.domain.api.RoundDTO;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import org.springframework.stereotype.Service;
import java.util.*;
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

    public HashMap<String, RoundsPerSingleSessionDTO> getAllRoundsForAllSessions() {

        return allRoundsForAllSessions;
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
