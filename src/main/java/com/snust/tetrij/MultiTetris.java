package com.snust.tetrij;

import javafx.application.Application;
import javafx.stage.Stage;

import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;

/**
 * 멀티모드의 wrapper 클래스
 */
public class MultiTetris extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        controller.runGame(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
