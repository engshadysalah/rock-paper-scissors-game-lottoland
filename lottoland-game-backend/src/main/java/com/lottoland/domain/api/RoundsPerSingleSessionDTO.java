package com.lottoland.domain.api;

import java.util.List;

public class RoundsPerSingleSessionDTO {

    private int roundNumbersPerSingleSession;
    private List<Round> allRoundsPerSingleSession;

    public int getRoundNumbersPerSingleSession() {
        return roundNumbersPerSingleSession;
    }

    public void setRoundNumbersPerSingleSession(int roundNumbersPerSingleSession) {
        this.roundNumbersPerSingleSession = roundNumbersPerSingleSession;
    }

    public List<Round> getAllRoundsPerSingleSession() {
        return allRoundsPerSingleSession;
    }

    public void setAllRoundsPerSingleSession(List<Round> allRoundsPerSingleSession) {
        this.allRoundsPerSingleSession = allRoundsPerSingleSession;
    }
}
