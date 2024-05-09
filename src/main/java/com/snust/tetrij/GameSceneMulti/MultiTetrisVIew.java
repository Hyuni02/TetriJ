package com.snust.tetrij.GameSceneMulti;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiTetrisVIew {

    public static void setScene(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane,1200, 800);
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();
    }
}
