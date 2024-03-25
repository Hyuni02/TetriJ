package com.snust.tetrij;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends Application {
    public static final int SIZE = 20;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * 20;
    public static final int [][] MESH = new int [XMAX/SIZE][YMAX/SIZE];
    private static Pane groupe = new Pane();
    private static Scene scene = new Scene(groupe, XMAX + 150, YMAX);
    public static int score = 0;
    public static int top = 0;
    private static boolean game = true;
    private static int linesNo = 0;
    private static enum difficulty {EASY, NORMAL, HARD};

    @Override
    public void start(Stage stage) throws IOException {
        for(int[] a:MESH){
            Arrays.fill(a,0);
        }

        Line line = new Line(XMAX,0,XMAX,YMAX);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arials;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arials;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        groupe.getChildren().addAll(scoretext, line, level);

        //첫 블록 생성
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();



    }

    private void TetrisKeyProc(){

    }

    public static void main(String[] args) {
        launch();
    }
}
