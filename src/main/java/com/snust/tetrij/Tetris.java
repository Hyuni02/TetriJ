package com.snust.tetrij;

import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    public static final int xmesh = XMAX/SIZE;
    public static final int ymesh = YMAX/SIZE;
    public static char [][] MESH = new char[YMAX/SIZE][XMAX/SIZE];
    public static Pane pane = new Pane();
    private static Scene scene = new Scene(pane, XMAX + 150, YMAX);
    public static int score = 0;
    private static boolean game = true;

    public static enum difficulty {EASY, NORMAL, HARD};

    @Override
    public void start(Stage stage) throws IOException {
        for(char[] a:MESH){
            Arrays.fill(a, '0');
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
        pane.getChildren().addAll(scoretext, line, level);

        //첫 블록 생성
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                scene.setOnKeyPressed(e->{
                    switch (e.getCode()) {
                        case DOWN -> Controller.moveDownOnKeyPress(Controller.bag.get(0));
                        case RIGHT -> Controller.moveRightOnKeyPress(Controller.bag.get(0));
                        case LEFT -> Controller.moveLeftOnKeyPress(Controller.bag.get(0));
                        case ESCAPE -> {
                            System.out.println("esc");
                            game = !game;
                        }
                    }
                });
                color_mesh();


            }
        };
        timer.schedule(task, 1000, 1000);

    }

    public static void main(String[] args) {
        launch();
    }

    private void color_mesh() {
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int y = 0; y < ymesh; y++) {
                            for (int x = 0; x < xmesh; x++) {
                                Rectangle r = new Rectangle(x*Tetris.SIZE, y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                                r.setFill(TetrominoBase.getColor(MESH[y][x]));
                                r.setStrokeWidth(1);
                                r.setStroke(Color.BLACK);
                                pane.getChildren().add(r);
                            }
                        }
                    }
                }
        );
    }

}
