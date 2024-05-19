package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.GameScene.GameControllerBase;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;

public class MultiTetrisController extends GameControllerBase {
    public final static MultiTetrisController controller = new MultiTetrisController();

    public MultiTetrisController() {
        super();
    }


    public void runGame(difficulty difficulty) {
        view.setScene();
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