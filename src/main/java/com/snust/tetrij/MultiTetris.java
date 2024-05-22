package com.snust.tetrij;

import javafx.application.Application;
import javafx.stage.Stage;

import com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;

import static com.snust.tetrij.GameScene.GameKeyController.addListenerPause;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;

/**
 * 멀티모드의 wrapper 클래스
 */
public class MultiTetris extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        controller.runGame(MultiTetrisController.difficulty.EASY);
    }

    public static void main(String args) {

        launch();
    }
}
