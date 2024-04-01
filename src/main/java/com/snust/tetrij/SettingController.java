package com.snust.tetrij;
import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingController extends GameManager {
    private Stage stage;
    private Scene scene;
    @FXML
    private ComboBox<String> resolutionComboBox;

    // 해상도 변경 메서드
    public static void setResolution(int width, int height) {
        resolutionX = width;
        resolutionY = height;
    }

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("start_menu.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}