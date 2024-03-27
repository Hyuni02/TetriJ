package com.snust.tetrij;

import javafx.fxml.FXML;

public class MenuController extends Tetris {
    @FXML
    protected void startTetris() {
        startGame();
    }
    @FXML
    protected void startSetting() {
        openSetting();
    }
    @FXML
    protected void startScoreboard() {
        openScoreboard();
    }

}
