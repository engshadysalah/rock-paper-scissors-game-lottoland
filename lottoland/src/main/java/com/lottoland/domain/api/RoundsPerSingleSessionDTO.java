package com.lottoland.domain.api;

import java.util.List;

public class RoundsPerSingleSessionDTO {

    private int roundNumbersPerSingleSession;
    private List<RoundDTO> allRoundsPerSingleSession;

    public int getRoundNumbersPerSingleSession() {
        return roundNumbersPerSingleSession;
    }

    public void setRoundNumbersPerSingleSession(int roundNumbersPerSingleSession) {
        this.roundNumbersPerSingleSession = roundNumbersPerSingleSession;
    }

    public List<RoundDTO> getAllRoundsPerSingleSession() {
        return allRoundsPerSingleSession;
    }

    public void setAllRoundsPerSingleSession(List<RoundDTO> allRoundsPerSingleSession) {
        this.allRoundsPerSingleSession = allRoundsPerSingleSession;
    }
}
