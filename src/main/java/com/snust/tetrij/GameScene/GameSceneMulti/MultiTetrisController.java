package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.GameScene.GameControllerBase;
import javafx.application.Platform;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;

public class MultiTetrisController extends GameControllerBase {
    public final static MultiTetrisController controller = new MultiTetrisController();

    public int loser = 0;
    PlayerThread p1;
    PlayerThread p2;

    public MultiTetrisController() {
        super();
    }


    public void runGame(difficulty difficulty) {
        view.setScene();
        currentDifficulty = difficulty;

        p1 = new PlayerThread(0, "p1");
        p2 = new PlayerThread(1, "p2");
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
        System.out.println("점수를 비교해 승리한 플레이거 판단");
        loser = 2; // 임시

        ShowWinner();
    }

    public void ShowWinner(){
        if(loser == 1){
            System.out.println("플레이어2 승리");
        }
        if(loser == 2){
            System.out.printf("플레이어1 승리");
        }
    }
}