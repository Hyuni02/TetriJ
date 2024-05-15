package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.GameScene.tetromino.TetrominoBase;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisModel.*;

public class MultiTetrisView {
    public final static MultiTetrisView view = new MultiTetrisView();

    private Scene scene;
    private static Pane pane;

    private StackPane[][] rect1 = new StackPane[20][10];
    private StackPane[][] rect2 = new StackPane[20][10];
    private StackPane[][][] rect = new StackPane[][][] {rect1, rect2};

    private final int WIDTH = 10;
    private final int  HEIGHT = 20;
    private final int size = 30;
    private int xmax;
    private int ymax;
    private final int offset = 320;

    private MultiTetrisView() {
        pane = new Pane();
        scene = new Scene(pane,1200, 800);

        Line line = new Line(xmax+ offset,0,xmax + offset, ymax + + offset);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(xmax + 5 + offset);
        Text lines = new Text("Lines: ");
        lines.setStyle("-fx-font: 20 arial;");
        lines.setY(100);
        lines.setX(xmax + 5 + offset);
        lines.setFill(Color.GREEN);

        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(150);
        pauseButton.setLayoutX(xmax + 5 + offset);
        pauseButton.setPrefWidth(50);
        pauseButton.setPrefHeight(25);
        pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
        pauseButton.setFocusTraversable(false);

//        Text keyText = new Text("왼쪽 이동: "+controller.leftKeyCode+"\n오른쪽 이동: "+controller.rightKeyCode
//                + "\n아래 이동: "+controller.downKeyCode + "\n회전: "+controller.rotateKeyCode
//                + "\n드롭 버튼: "+controller.dropKeyCode);
//        keyText.setStyle("-fx-font: 10 arial;");
//        keyText.setY(300 + offset/5);
//        keyText.setX(xmax + 5 + offset);
        pane.getChildren().addAll(scoretext, line, lines, pauseButton);

        Line line2 = new Line(810,0,810, ymax + offset);
        Text scoretext2 = new Text("Score: ");
        scoretext2.setStyle("-fx-font: 20 arial;");
        scoretext2.setY(50);
        scoretext2.setX(815);
        Text lines2 = new Text("Lines: ");
        lines2.setStyle("-fx-font: 20 arial;");
        lines2.setY(100);
        lines2.setX(815);
        lines2.setFill(Color.GREEN);

//        Button pauseButton2 = new Button("Pause");
//        pauseButton2.setLayoutY(150);
//        pauseButton2.setLayoutX(xmax + 5 + offset);
//        pauseButton2.setPrefWidth(50);
//        pauseButton2.setPrefHeight(25);
//        pauseButton2.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
//        pauseButton2.setFocusTraversable(false);
        pane.getChildren().addAll(scoretext2, line2, lines2);

        for (StackPane[] sp: rect1)
            Arrays.fill(sp, new StackPane());

        for (StackPane[] sp: rect2)
            Arrays.fill(sp, new StackPane());
    }

    public void setScene(Stage stage) {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(x* size, y*size, size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x*size);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rect1[y][x] = sp;
                }
            }
        });

        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(x* size, y*size, size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x*size + 500);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rect2[y][x] = sp;
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("TETRIS");
        controller.addListener(scene);
        stage.show();
    }

    public void color_mesh(int player) {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = (Rectangle)rect[player][y][x].getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(model.MESH[player][y][x]));
                    Text t = (Text)rect[player][y][x].getChildren().get(1);
                    if (model.MESH[player][y][x] == 'L'){
                        t.setText("L");
                    }
                    else{
                        t.setText(" ");
                    }
                }
            }
        });
    }
}