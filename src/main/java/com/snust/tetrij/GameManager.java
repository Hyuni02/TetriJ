package com.snust.tetrij;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class GameManager {
    public static int resolutionX = 800;
    public static int resolutionY = 600;

    protected void startGame(String diff) throws Exception{
        MainMenu.playTetrij(diff);
    }

    protected Parent returnSceneRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        return root;
    }
}
