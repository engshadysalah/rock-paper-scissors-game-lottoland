package com.lottoland.domain.api;

public class Round {

    public static final String FIRST_PLAYER = "Player_1";
    public static final String SECOND_PLAYER = "Player_2";
    public static final String  DRAW= "Draw";
    public static final String  TOTAL_ROUNDS_PLAYED= "totalRoundsPlayed";
    private String firstPlayerMove;
    private static final String SECOND_PLAYER_MOVE = Move.ROCK.getValue();
    private String roundResult;

    private boolean isRestarted;

    public String getFirstPlayerMove() {
        return firstPlayerMove;
    }

    public void setFirstPlayerMove(String firstPlayerMove) {
        this.firstPlayerMove = firstPlayerMove;
    }

    public String getSecondPlayerMove() {
        return SECOND_PLAYER_MOVE;
    }

    public String getRoundResult() {
        return roundResult;
    }

    public void setRoundResult(String roundResult) {
        this.roundResult = roundResult;
    }

    public boolean isRestarted() {
        return isRestarted;
    }

    public void setRestarted(boolean restarted) {
        isRestarted = restarted;
    }
}
