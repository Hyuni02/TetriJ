package com.snust.tetrij.GameSceneSingle;

import com.snust.tetrij.GameControllerBase;
import com.snust.tetrij.GameScene.TetrisBoardController;
import com.snust.tetrij.GameSceneMulti.MultiTetrisController;
import com.snust.tetrij.Tetris;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.snust.tetrij.Controller.GameOverController.switchToGameOver;
import static com.snust.tetrij.GameSceneMulti.MultiBoardController.boardController;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisView.view;
import static com.snust.tetrij.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController extends GameControllerBase {
    public final static SingleTetrisController controller_s = new SingleTetrisController();

    public SingleTetrisController() {
        super();
    }


    public void runGame(MultiTetrisController.difficulty difficulty) {
        view_s.setScene();
        currentDifficulty = difficulty;

        Runnable task = new Runnable() {
            public void run() {
                TetrisBoardController.generateTetromino();
                TetrisBoardController.generateTetromino();
                view_s.color_mesh();

                int speedLevel = 0;
                while (!isGameOver) {
                    int freq = 300;
                    try {
                        //일시정지
                        if (isPaused)
                            continue;

                        int finalFreq = 0;
                        int boost = 30;
                        switch (controller_s.currentDifficulty) {
                            case EASY -> finalFreq = freq - speedLevel * (int) (boost * 0.8f);
                            case HARD -> finalFreq = freq - speedLevel * (int) (boost * 1.2f);
                            default -> finalFreq = freq - speedLevel * boost; //normal or item
                        }
                        Thread.sleep(finalFreq);

                        if (speedLevel == 0)
                            score++;
                        else if (speedLevel == 1)
                            score += 2;
                        else if (speedLevel == 2)
                            score += 3;

                        view_s.scoreText.setText("Score: " + Integer.toString(score));
                        view_s.level.setText("Lines: " + Integer.toString(linesNo));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    TetrisBoardController.softDrop(TetrisBoardController.bag.get(0)); //한칸 드랍
                    view_s.color_mesh(); //색칠

                    //게임오바
                    if (Tetris.top >= Tetris.HEIGHT - 1) {
                        Thread.interrupt();
                    }
                }
                // GameOver(stage, dif);
            }
        };
        thread = new Thread(task);
        thread.start();
    }
}
