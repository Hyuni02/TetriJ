package com.snust.tetrij;

import com.snust.tetrij.Controller.ResolutionManager;
import com.snust.tetrij.GameScene.GameControllerBase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import static com.snust.tetrij.Controller.ResolutionManager.resolutionInitialize;
import static com.snust.tetrij.Controller.ResolutionManager.curHeight;
import static com.snust.tetrij.Controller.ResolutionManager.curWidth;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;

import org.aspectj.lang.Aspects;
public class MainMenu extends Application {
    private final static GameManager instance = GameManager.getInstance();
    private static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(MainMenu.class.getResource("/com/snust/tetrij/Font/kenvector_future.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
        Scene scene = new Scene(root);
        instance.setPrimaryStage(stage);
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

        // 초기 해상도 설정
        resolutionInitialize();
        stage.setHeight(curHeight);
        stage.setWidth(curWidth);
        ResolutionManager.setStartMenuResolution(root, (int) stage.getHeight(), (int) stage.getWidth());

        // 해상도 변경되지 않도록 창을 드래그하여 늘리는 기능 false
        stage.setResizable(false);
        // stage.setFullScreen(true);
        //playSound("src/main/resources/com/snust/tetrij/sound/startMenuBGM.wav");
        //mediaPlayer.setVolume(0.3);

    }

    public static void playTetrij(String difficulty) throws Exception {
        if(difficulty == "EASY") {
            controller_s.runGame(instance.getPrimaryStage(), GameControllerBase.difficulty.EASY);
        }
        if(difficulty == "NORMAL") {
            controller_s.runGame(instance.getPrimaryStage(), GameControllerBase.difficulty.NORMAL);
        }
        if(difficulty == "HARD") {
            controller_s.runGame(instance.getPrimaryStage(), GameControllerBase.difficulty.HARD);
        }
        if(difficulty == "ITEM") {
            controller_s.runGame(instance.getPrimaryStage(), GameControllerBase.difficulty.ITEM);
        }
        System.out.println("싱글(" + difficulty + ")");
    }

    private void playSound(String fileName) {
        try {
            Media sound = new Media(new File(fileName).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);

            mediaPlayer.setOnEndOfMedia(() -> {
                if (mediaPlayer != null) {
                    mediaPlayer.seek(Duration.ZERO); // 노래를 처음으로 돌려놓고
                    mediaPlayer.play(); // 다시 재생
                }
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
        Aspects.aspectOf(TimeTest2.class);
        launch(args);
    }
}