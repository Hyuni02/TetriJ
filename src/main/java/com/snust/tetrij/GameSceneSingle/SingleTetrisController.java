package com.snust.tetrij.GameSceneSingle;

import com.snust.tetrij.GameSceneMulti.MultiTetrisController;
import com.snust.tetrij.GameSceneMulti.PlayerThread;

import static com.snust.tetrij.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController {
    public final static SingleTetrisController controller_s = new SingleTetrisController();

    public boolean isPaused = false;
    public boolean isGameOver = false;
    public enum difficulty {EASY, NORMAL, HARD, ITEM};
    public MultiTetrisController.difficulty currentDifficulty;

    public SingleTetrisController() { }


    public void runGame(MultiTetrisController.difficulty difficulty) {
        view_s.setScene();
        currentDifficulty = difficulty;

        PlayerThread p1 = new PlayerThread(0, "p1");
        PlayerThread p2 = new PlayerThread(1, "p2");
        p1.start();
        p2.start();
//      try {
//            p1.join();
//            p2.join();
//        } catch (InterruptedException e) {
//            System.out.println("Fatal Error: " + e.getMessage());
//        }

    }
}
