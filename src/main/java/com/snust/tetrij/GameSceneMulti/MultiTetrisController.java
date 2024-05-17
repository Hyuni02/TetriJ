package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.snust.tetrij.GameSceneMulti.MultiTetrisView.view;

public class MultiTetrisController {
    public final static MultiTetrisController controller = new MultiTetrisController();

    public boolean isPaused = false;
    public boolean isGameOver = false;
    public enum difficulty {EASY, NORMAL, HARD, ITEM};
    public difficulty currentDifficulty;

    public MultiTetrisController() { }


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