package com.snust.tetrij;


public class GameControllerBase {
    public boolean isPaused;
    public boolean isGameOver;
    public enum difficulty {EASY, NORMAL, HARD, ITEM};
    public difficulty currentDifficulty;
    public int score;
    public int linesNo;

    public GameControllerBase() {
        isPaused = false;
        isGameOver = false;
        score = 0;
        linesNo = 0;
    }
}
