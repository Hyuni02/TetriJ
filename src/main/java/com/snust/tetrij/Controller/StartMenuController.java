package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import javafx.scene.control.Alert;

import com.snust.tetrij.Controller.SelectModeController;

public class StartMenuController {
    private final GameManager instance = GameManager.getInstance();
    private MediaPlayer mediaPlayer;
    private Stage stage;
    private Scene scene;

    @FXML
    protected void startTetris() throws Exception{// 모드 선택 창을 열기 위한 FXMLLoader
        SelectModeController.getInstance().selectMode();
    }
//    @FXML
//    public void switchToStartMenu(ActionEvent event) throws IOException {
//        Parent root = instance.loadFXML("start_menu.fxml");
//        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ESCAPE) {
//                    stage.close(); // ESC 키를 누르면 창을 닫음
//                }
//            }
//        });
//        stage.show();
//        com.snust.tetrij.SetResolution.setStartMenuResolution(root, (int) stage.getHeight(), (int) stage.getWidth());
//    }
@FXML
public void switchToStartMenu() throws IOException {
    instance.switchToScene("start_menu.fxml");
}

    private void setupEscKeyHandler(Scene scene, Stage stage) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
    }

//    private void switchToScene(Stage stage, String fxml) throws IOException {
//        Parent root = instance.loadFXML(fxml);
//        Scene newScene = new Scene(root);
//        stage.setScene(newScene);
//        setupEscKeyHandler(newScene, stage);
//        stage.show();
//    }

    @FXML
    public void switchToScoreBoard() throws IOException {
        ClickButtonSound();
        instance.switchToScene("score_board.fxml");
    }
    @FXML
    public void switchToSetting() throws IOException {
        ClickButtonSound();
        instance.switchToScene("setting.fxml");

    }
    @FXML
    private void exitGame() {
        ClickButtonSound();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게임 종료");
        alert.setHeaderText("게임을 종료하시겠습니까?");
        alert.setContentText("게임을 종료하려면 확인을 누르세요.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
    @FXML
    private void onButtonSound() {
//        try {
//            Media sound = new Media(new File("src/main/resources/com/snust/tetrij/sound/onButton.mp3").toURI().toString());
//
//            mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.setVolume(1.0);
//            mediaPlayer.play();
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//            onButtonSound_build();
//        }
    }

    private void onButtonSound_build() {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            URL soundUrl = getClass().getResource("/com/snust/tetrij/sound/onButton.mp3");
            if (soundUrl == null) {
                System.err.println("사운드 파일을 찾을 수 없습니다.");
                return;
            }

            Media sound = new Media(soundUrl.toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(1.0);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ClickButtonSound() {
//        try {
//            Media sound = new Media(new File("src/main/resources/com/snust/tetrij/sound/button_click.mp3").toURI().toString());
//
//            mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.setVolume(0.5);
//            mediaPlayer.play();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
