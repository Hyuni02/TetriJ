package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.GameScene.GameSceneSingle.PlayerThreadSingle;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel.model;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;

public class MultiTetrisController extends GameControllerBase {
    public final static MultiTetrisController controller = new MultiTetrisController();

    PlayerThread p1;
    PlayerThread p2;

    public MultiTetrisController() {
        super();
    }


    public void runGame(difficulty difficulty) {
        view.initView();
        model.initModel();

        view.setScene();
        currentDifficulty = difficulty;

        p1 = new PlayerThread(0, "p1");
        p2 = new PlayerThread(1, "p2");
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