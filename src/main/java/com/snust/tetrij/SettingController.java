package com.snust.tetrij;
import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingController extends GameManager {
    private Stage stage;
    private Scene scene;

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("start_menu.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}