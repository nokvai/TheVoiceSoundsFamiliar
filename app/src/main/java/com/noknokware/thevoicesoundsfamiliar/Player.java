package com.noknokware.thevoicesoundsfamiliar;

public class Player {

    private int playerId;
    private String playerName;
    private String gender;
    private int finalScore;

    public Player() { }

    public Player(int playerId, String playerName, String gender, int finalScore) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.gender = gender;
        this.finalScore = finalScore;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

}
