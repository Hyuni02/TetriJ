package com.snust.tetrij;

import com.snust.tetrij.GameSceneMulti.MultiTetrisVIew;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiTetris extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MultiTetrisVIew.setScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
