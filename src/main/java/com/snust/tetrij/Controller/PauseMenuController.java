package com.snust.tetrij.Controller;
import com.snust.tetrij.GameManager;
import com.snust.tetrij.SingleTetris;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;

public class PauseMenuController extends SingleTetris {
    private final static GameManager instance = GameManager.getInstance();
    @FXML
    public void exit_game() {
        System.exit(0);
    }

    @FXML
    public void continue_game(ActionEvent event) {
        // 현재 게임 일시정지 -> isPaused를 false로 설정하여 게임이 계속될 수 있도록 함
        controller_s.isPaused = false;
        controller_s.onPauseButton = false;
        controller.isPaused = false;
        controller.onPauseButton = false;
//        System.out.println("test"+ Tetris.isPaused);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        controller_s.isPaused = false;
        controller_s.onPauseButton = false;
        controller_s.stopGame();
        controller.isPaused = false;
        controller.onPauseButton = false;
        controller.stopGame();
        instance.switchToScene("start_menu.fxml");
    }
}
