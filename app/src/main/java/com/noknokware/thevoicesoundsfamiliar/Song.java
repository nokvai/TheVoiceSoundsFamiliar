package com.noknokware.thevoicesoundsfamiliar;

public class Song {

    private int songPlayerId;
    private String songName;
    private int songIndex;
    private String songLevel;
    private int songIsCorrect;
    private int songUsedHint;
    private int songScore;
    private int songIsFinished;

    public Song() {
    }

    public Song(int songPlayerId, String songName, int songIndex, String songLevel, int songIsCorrect, int songUsedHint, int songScore, int songIsFinished) {
        this.songPlayerId = songPlayerId;
        this.songName = songName;
        this.songIndex = songIndex;
        this.songLevel = songLevel;
        this.songIsCorrect = songIsCorrect;
        this.songUsedHint = songUsedHint;
        this.songScore = songScore;
        this.songIsFinished = songIsFinished;
    }


    public int getSongPlayerId() {
        return songPlayerId;
    }

    public void setSongPlayerId(int songPlayerId) {
        this.songPlayerId = songPlayerId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(int songIndex) {
        this.songIndex = songIndex;
    }

    public String getSongLevel() {
        return songLevel;
    }

    public void setSongLevel(String songLevel) {
        this.songLevel = songLevel;
    }

    public int getSongIsCorrect() {
        return songIsCorrect;
    }

    public void setSongIsCorrect(int songIsCorrect) {
        this.songIsCorrect = songIsCorrect;
    }

    public int getSongUsedHint() {
        return songUsedHint;
    }

    public void setSongUsedHint(int songUsedHint) {
        this.songUsedHint = songUsedHint;
    }

    public int getSongScore() {
        return songScore;
    }

    public void setSongScore(int songScore) {
        this.songScore = songScore;
    }

    public int getSongIsFinished() {
        return songIsFinished;
    }

    public void setSongIsFinished(int songIsFinished) {
        this.songIsFinished = songIsFinished;
    }

}
