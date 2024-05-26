package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.Controller.SelectModeController;
import com.snust.tetrij.Controller.showWinnerController;
import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.MultiTetris;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import javafx.stage.Stage;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel.model;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;
import com.snust.tetrij.Controller.showWinnerController;

public class MultiTetrisController extends GameControllerBase {
    public final static MultiTetrisController controller = new MultiTetrisController();
    showWinnerController winnerController = new showWinnerController();

    Stage thisStage;
    Scene thisScene;

    public int loser = 0;
    public boolean timeout = false;
    public int[] tops = {0, 0};

    public MultiTetrisController() {
        super();
    }


    public void runGame(difficulty difficulty) {
        view.initView();
        model.initModel();

        view.setScene();
        currentDifficulty = difficulty;

        PlayerThread p1 = new PlayerThread(0, "p1");
        PlayerThread p2 = new PlayerThread(1, "p2");
        p1.start();
        p2.start();

        System.out.println("배틀("+difficulty.toString() + ")");
        if(difficulty == GameControllerBase.difficulty.TIME){
            TimerThread timer = new TimerThread();
            timer.start();
        }
//      try {
//            p1.join();
//            p2.join();
//        } catch (InterruptedException e) {
//            System.out.println("Fatal Error: " + e.getMessage());
//        }

    }

    public void CheckWinner(){
        System.out.println("점수를 비교해 승리한 플레이어 판단");
        if(tops[0]<tops[1]){
            loser = 1;
        }
        else if(tops[0]>tops[1]){
            loser = 0;
        }
        else{
            loser = -1;
        }
        ShowWinner();
    }

    public void ShowWinner(){
        if(loser == 0){
            System.out.println("플레이어2 승리");
        }
        if(loser == 1){
            System.out.println("플레이어1 승리");
        }
        if(loser == -1){
            System.out.println("무승부");
        }
        
        //todo 승리자 fxml 띄우기
        winnerController.showWinnerFXML(loser);
    }

    public void togglePause() {
        System.out.println("Pause");
        Platform.runLater( () -> {
            if (!controller.onPauseButton) {
                controller.isPaused = true;
                controller.onPauseButton = !controller.isPaused;
                if (controller.isPaused) {
                    try{
                        controller.onPauseButton = true;
                        FXMLLoader fxmlLoader = new FXMLLoader(MultiTetris.class.getResource("pause_menu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage pauseStage = new Stage();
                        pauseStage.setScene(new Scene(root));
                        pauseStage.setTitle("Pause");
                        pauseStage.setOnCloseRequest(
                                event -> {
                                    //pause 창이 닫힐 때
                                    controller.isPaused = false;
                                    controller.onPauseButton = false;
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