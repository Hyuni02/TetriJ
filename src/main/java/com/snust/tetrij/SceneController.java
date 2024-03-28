package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SceneController extends GameManager {
    private Stage stage;
    private Scene scene;
    @FXML
    protected void startTetris() {
        startGame();
    }
    @FXML
    protected void startScoreboard() {
        openScoreboard();
    }
    @FXML
    public void switchToStartmenu(ActionEvent event) throws IOException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(423, 322);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToScoreboard(ActionEvent event) throws IOException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(423, 322);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(423, 322);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
