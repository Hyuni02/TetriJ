package com.snust.tetrij.GameScene.GameSceneMulti;

import javafx.application.Platform;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;

public class TimerThread extends Thread{
    public int remain_time = 120;
    public TimerThread(){
        super("Timer");
    }

    @Override
    public void run(){
        while(remain_time > 0 && !controller.isGameOver){
            if(controller.isPaused){
                continue;
            }
            System.out.println(remain_time);
            remain_time--;
            try {
                sleep(1000);
            }
            catch (Exception e) {

            }
        }
        if(!controller.isGameOver) {
            controller.timeout = true;
            controller.isGameOver = true;
            Platform.runLater(()->{
                controller.CheckWinner();
                controller.timeout = false;
            });
        }
    }
}
