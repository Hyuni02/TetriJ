package com.snust.tetrij;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class GameManager {
    public static int resolutionX = 800;
    public static int resolutionY = 600;

    protected void startGame() throws Exception{
        Tetris_Temp.playTetrij();
    }

    protected Parent returnSceneRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        return root;
    }
}
