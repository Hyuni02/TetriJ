package com.snust.tetrij;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PauseMenuController extends InGame {
    @FXML
    public void exit_game() {
        System.exit(0);
    }

    @FXML
    public void continue_game(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

        // 현재 게임 일시정지 -> isPaused를 false로 설정하여 게임이 계속될 수 있도록 함
        Tetris.isPaused = false;
        Tetris.onPauseButton = false;
    }
    @FXML
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Tetris.isPaused = false;
        Tetris.onPauseButton = false;
        Tetris.switchToStartMenu();
    }
}
