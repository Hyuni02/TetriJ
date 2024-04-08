package com.snust.tetrij;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class Tetris extends Application {
    private static MediaPlayer mediaPlayer;

    public static Stage curStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
        Scene scene = new Scene(root);
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
        // stage.setResizable(false);
        // stage.setFullScreen(true);
        //playSound("src/main/resources/com/snust/tetrij/sound/startMenuBGM.wav");
        //mediaPlayer.setVolume(0.3);
        curStage = stage;
    }

    public static void playTetrij() throws Exception {
        // 여기다가 게임 로직 넣어주세여
        InGame.newGameScene(curStage);
        System.out.println("게임 시작");
    }

    private void playSound(String fileName) {
        try {
            Media sound = new Media(new File(fileName).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO); // 노래를 처음으로 돌려놓고
                mediaPlayer.play(); // 다시 재생
            });
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopSound() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose(); // 리소스 해제
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}