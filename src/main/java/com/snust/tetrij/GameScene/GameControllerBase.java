package com.snust.tetrij.GameScene;


public class GameControllerBase {
    public boolean isPaused;
    public boolean isGameOver;
    public enum difficulty {EASY, NORMAL, HARD, ITEM};
    public difficulty currentDifficulty;
    public int score;
    public int linesNo;
    public int top;
    public int deleted_lines;

    public boolean color_weakness;

    public boolean onPauseButton;

    public GameControllerBase() {
        onPauseButton = false;
        isPaused = false;
        isGameOver = false;
        score = 0;
        linesNo = 0;
        top = 0;
        deleted_lines = 0;
        isGameOver = false;
    }
}
