package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
public class StartMenuController extends GameManager {
    private Stage stage;
    private Scene scene;

    @FXML
    protected void startTetris() throws Exception{
        startGame();
    }
    @FXML
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("start_menu.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToScoreBoard(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("score_board.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("setting.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void exitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게임 종료");
        alert.setHeaderText("게임을 종료하시겠습니까?");
        alert.setContentText("게임을 종료하시려면 확인을 누르세요.");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}
