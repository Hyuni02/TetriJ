package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.MultiTetris;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController extends GameControllerBase {
    public final static SingleTetrisController controller_s = new SingleTetrisController();
    private volatile boolean running = false; // 쓰레드 실행 상태를 관리하는 플래그
    public PlayerThreadSingle playerThread;

    public SingleTetrisController() {
        super();
    }


    public void runGame(Stage stage, difficulty difficulty) {
        view_s.initView();
        model_s.initModel();
        initController();
        System.out.println("init");
        view_s.setScene();
        view_s.stage = stage;
        currentDifficulty = difficulty;

        view_s.stage.setOnCloseRequest(event->{
            controller_s.isGameOver = true;
            stopGame(); // 게임 창이 닫힐 때 쓰레드 정지
        });

//        SingleKeyController.addListenerGameControl(view_s.scene);

        startGame();
    }

    private void startGame() {
        if (running) return; // 이미 게임이 실행 중이면 시작하지 않음
        System.out.println("start");
        running = true;
        controller_s.isGameOver = false;
        playerThread = new PlayerThreadSingle("Single Play");
        playerThread.start();
    }

    public void stopGame() {
        if (!running) return; // 게임이 실행 중이 아니면 종료하지 않음
        System.out.println("stop");
        controller_s.isGameOver = true;
        try {
            playerThread.join(); // 쓰레드가 종료될 때까지 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void togglePause() {
        System.out.println("Pause");
        Platform.runLater( () -> {
            if (!controller_s.onPauseButton) {
                controller_s.isPaused = true;
                controller_s.onPauseButton = !controller_s.isPaused;
                if (controller_s.isPaused) {
                    try{
                        controller_s.onPauseButton = true;
                        FXMLLoader fxmlLoader = new FXMLLoader(MultiTetris.class.getResource("pause_menu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage pauseStage = new Stage();
                        pauseStage.setScene(new Scene(root));
                        pauseStage.setTitle("Pause");
                        pauseStage.setOnCloseRequest(
                                event -> {
                                    //pause 창이 닫힐 때
                                    controller_s.isPaused = false;
                                    controller_s.onPauseButton = false;
                                }
                        );
                        pauseStage.getScene().setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ESCAPE) {
                                pauseStage.close();
                                Platform.exit();
                            }
                        });
                        pauseStage.showAndWait();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

    }
}
