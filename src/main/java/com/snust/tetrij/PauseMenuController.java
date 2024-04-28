package com.snust.tetrij;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PauseMenuController extends Tetris {
    private final static GameManager instance = GameManager.getInstance();
    @FXML
    public void exit_game() {
        System.exit(0);
    }

    @FXML
    public void continue_game(ActionEvent event) {
        // 현재 게임 일시정지 -> isPaused를 false로 설정하여 게임이 계속될 수 있도록 함
        Tetris.isPaused = false;
        Tetris.onPauseButton = false;
//        System.out.println("test"+ Tetris.isPaused);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Tetris.isPaused = false;
        Tetris.onPauseButton = false;

        instance.switchToScene("start_menu.fxml");
    }
}
