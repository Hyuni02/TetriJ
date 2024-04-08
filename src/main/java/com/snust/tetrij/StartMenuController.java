package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class StartMenuController extends GameManager {
    private MediaPlayer mediaPlayer;
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
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close(); // ESC 키를 누르면 창을 닫음
                }
            }
        });
        stage.show();
    }
    @FXML
    public void switchToScoreBoard(ActionEvent event) throws IOException {
        ClickButtonSound();
        Parent root = returnSceneRoot("score_board.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {
        ClickButtonSound();
        Parent root = returnSceneRoot("setting.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void exitGame() {
        ClickButtonSound();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게임 종료");
        alert.setHeaderText("게임을 종료하시겠습니까?");
        alert.setContentText("게임을 종료하려면 확인을 누르세요.");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
    @FXML
    private void onButtonSound() {
        try {
            Media sound = new Media(new File("src/main/resources/com/snust/tetrij/sound/onButton.mp3").toURI().toString());

            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(1.0);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ClickButtonSound() {
        try {
            Media sound = new Media(new File("src/main/resources/com/snust/tetrij/sound/button_click.mp3").toURI().toString());

            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
