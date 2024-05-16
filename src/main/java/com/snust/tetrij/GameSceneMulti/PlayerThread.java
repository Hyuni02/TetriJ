package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;

import static com.snust.tetrij.GameSceneMulti.MultiBoardController.boardController;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;

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

            boardController.generateTetromino(player_num);
            boardController.generateTetromino(player_num);
            MultiTetrisView.view.color_mesh(player_num);

            try {
                this.sleep(1000);
            } catch (InterruptedException e) {

            }
            boardController.softDrop((TetrominoBase) MultiTetrisModel.model.bags[player_num].get(0), player_num);


        }
    }
}
