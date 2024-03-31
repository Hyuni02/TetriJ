package com.snust.tetrij;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import com.snust.tetrij.GameManager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class Main extends Application {
    private MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            playSound("src/main/resources/com/snust/tetrij/sound/startMenuBGM.wav");

    }
    protected void playTetrij() {
        System.out.println("게임 시작");
    }
    protected void startGame() {
        playTetrij();
    }

    protected Parent returnSceneRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        return root;
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