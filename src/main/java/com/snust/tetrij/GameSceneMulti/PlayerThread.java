package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.GameScene.tetromino.TetrominoBase;

import static com.snust.tetrij.GameSceneMulti.MultiBoardController.softDrop;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisView.view;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisModel.*;

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
        while (!this.isInterrupted()) {
            if (controller.isPaused)
                continue;

            MultiBoardController.generateTetromino(player_num);
            MultiBoardController.generateTetromino(player_num);
            view.color_mesh(player_num);

            try {
                this.sleep(100);
            } catch (InterruptedException e) {

            }
            softDrop((TetrominoBase) model.bags[player_num].get(0), player_num);


        }
    }
}
