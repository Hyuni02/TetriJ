package com.snust.tetrij;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GameManager extends Main {
    protected int _resolutionX = 800;
    protected int _resolutionY = 600;
    protected int _score = 0;
    protected void startGame() {
        playTetriJ();
    }

    protected Parent returnSceneRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        return root;
    }

}
