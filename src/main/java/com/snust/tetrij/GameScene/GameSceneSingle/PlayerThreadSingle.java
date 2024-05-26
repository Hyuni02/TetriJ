package com.snust.tetrij.GameScene.GameSceneSingle;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Platform;

import static com.snust.tetrij.Controller.GameOverController.switchToGameOver;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleBoardController.boardController_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class PlayerThreadSingle extends Thread {
    String thread_name;

    public PlayerThreadSingle(String thread_name) {
        super(thread_name);
        this.thread_name = thread_name;
    }


    @Override
    public void run() {
        boardController_s.generateTetromino();
        boardController_s.generateTetromino();

        int speedLevel = 0;
        while (!controller_s.isGameOver) {
            if (controller_s.isPaused)
                continue;

            int finalFreq = 0;
            int freq = 300;
            int boost = 30;
            switch (controller_s.currentDifficulty) {
                case EASY -> finalFreq = freq - speedLevel * (int) (boost * 0.8f);
                case HARD -> finalFreq = freq - speedLevel * (int) (boost * 1.2f);
                default -> finalFreq = freq - speedLevel * boost; //normal or item
            }
//            SingleBoardController.bag.get(0).update_mesh(-1);
            view_s.color_mesh();
            try {
                this.sleep(finalFreq);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (speedLevel == 0)
                controller_s.score++;
            else if (speedLevel == 1)
                controller_s.score += 2;
            else if (speedLevel == 2)
                controller_s.score += 3;

            //속도조절
            if (controller_s.linesNo <= 5) {
                speedLevel = 0;
            } else if (controller_s.linesNo <= 10) {
                speedLevel = 1;
            } else {
                speedLevel = 2;
            }

            boardController_s.softDrop(SingleTetrisModel.model_s.bag.get(0)); //한칸 드랍
            view_s.color_mesh();
            view_s.scoretext.setText("Score: " + controller_s.score);
            view_s.lines.setText("Lines: " + controller_s.linesNo);

            if (controller_s.top >= view_s.HEIGHT - 2)
                controller_s.isGameOver = true;
        }

        Platform.runLater(()->{
            switchToGameOver(controller_s.score, controller_s.currentDifficulty);
        });
    }
}
