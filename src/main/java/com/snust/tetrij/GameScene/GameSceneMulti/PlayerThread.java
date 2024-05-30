package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Platform;

import java.io.IOException;

import static com.snust.tetrij.Controller.GameOverController.switchToGameOver;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiBoardController.boardController;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;

public class PlayerThread extends Thread {
    int player_num;
    String thread_name;

    public PlayerThread(int player_num, String thread_name) {
        super(thread_name);
        this.player_num = player_num;
        this.thread_name = thread_name;
    }


    @Override
    public void run() {
        System.out.println("run");
        boardController.generateTetromino(player_num);
        boardController.generateTetromino(player_num);

        int speedLevel = 0;
        while (!controller.isGameOver) {
            if (controller.isPaused) {
                continue;
            }

            //속도 조절
            int finalFreq;
            int boost = 30;
            int freq = 300;
            switch (controller.currentDifficulty) {
                case EASY -> finalFreq = freq - speedLevel * (int) (boost * 0.8f);
                case HARD -> finalFreq = freq - speedLevel * (int) (boost * 1.2f);
                default -> finalFreq = freq - speedLevel * boost; //normal or item
            }
//            view.color_mesh(player_num);


            //점수 증가
            if (speedLevel == 0)
                controller.score++;
            else if (speedLevel == 1)
                controller.score += 2;
            else if (speedLevel == 2)
                controller.score += 3;

            //속도조절
            if (controller.linesNo <= 5) {
                speedLevel = 0;
            } else if (controller.linesNo <= 10) {
                speedLevel = 1;
            } else {
                speedLevel = 2;
            }

            //한칸 드랍하고 색칠
            boardController.softDrop((TetrominoBase) MultiTetrisModel.model.bags[player_num].get(0), player_num);
            view.color_mesh(player_num);
            if (controller.tops[player_num] >= view.HEIGHT - 3) {
                controller.isGameOver = true;
                controller.loser = player_num;
                Platform.runLater(() -> {
                    if(controller.timeout){
                        return;
                    }
                    controller.ShowWinner();
                });
            }
            try {
                this.sleep(finalFreq);
            } catch (InterruptedException e) {

            }
        }
//        this.interrupt();
//        Platform.runLater(() -> {
//            controller.stopGame();
//            if(controller.timeout){
//                return;
//            }
//            if(thread_name == "p1") {
//                    controller.ShowWinner();
//            }
//        });
    }
}
