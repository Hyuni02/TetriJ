package com.snust.tetrij;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.sound.sampled.*;
import java.io.IOException;

public class GameManager extends Main {
    protected int resolutionX = 800;
    protected int resolutionY = 600;
    protected int score = 0;

    protected void startGame() {
        playTetrij();
    }

    protected Parent returnSceneRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        return root;
    }
}
