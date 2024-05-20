package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameControllerBase;
import javafx.stage.Stage;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController extends GameControllerBase {
    public final static SingleTetrisController controller_s = new SingleTetrisController();

    public SingleTetrisController() {
        super();
    }


    public void runGame(Stage stage, difficulty difficulty) {
        view_s.setScene();
        view_s.stage = stage;
        currentDifficulty = difficulty;

        Thread thread = null;
        Thread finalThread = thread;

        SingleKeyController.addListenerGameControl(view_s.scene);

        Runnable task = new Runnable() {
            public void run() {
                SingleBoardController.generateTetromino();
                SingleBoardController.generateTetromino();
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

                    SingleBoardController.softDrop(SingleBoardController.bag.get(0)); //한칸 드랍
                    view_s.color_mesh(); //색칠

                    //게임오바
                    if (controller_s.top > 19) {
                        finalThread.interrupt();
                    }
                }
                // GameOver(stage, dif);
            }
        };
        thread = new Thread(task);
        thread.start();
    }
}
