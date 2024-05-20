package com.snust.tetrij;

import com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;

public class SingleTetris extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        controller_s.runGame(stage, MultiTetrisController.difficulty.EASY);
    }

    public static void main(String args) {
        launch();
    }
}
