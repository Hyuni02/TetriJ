package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.GameManager;
import com.snust.tetrij.GameScene.GameSceneSingle.SingleBoardController;
import com.snust.tetrij.tetromino.TetrominoBase;
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

import static com.snust.tetrij.Controller.ResolutionManager.curHeight;
import static com.snust.tetrij.Controller.ResolutionManager.curWidth;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiKeyController.keyController;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel.model;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;

public class MultiTetrisView {

    private final static GameManager instance = GameManager.getInstance();
    public final static MultiTetrisView view = new MultiTetrisView();

    public Scene scene;
    private static Pane pane;
    private Stage stage;

    private StackPane[][] rect1 = new StackPane[20][10];
    private StackPane[][] rect2 = new StackPane[20][10];
    public StackPane[][][] rect = new StackPane[][][] {rect1, rect2};

    private StackPane[][] nextRect1 = new StackPane[4][4];
    private StackPane[][] nextRect2 = new StackPane[4][4];
    public StackPane[][][] nextRect = new StackPane[][][] {nextRect1, nextRect2};

    private StackPane[][] buffer1 = new StackPane[10][10];
    private StackPane[][] buffer2 = new StackPane[10][10];
    public StackPane[][][] buffer = new StackPane[][][] {buffer1, buffer2};

    public final int WIDTH = 10;
    public final int HEIGHT = 20;
    private int size;
    private int bufferSize;
    private int panelOffset = 500;

    private int lineX = 810;
    private boolean isPaused;
    private boolean onPauseButton;

    private MultiTetrisView() {

    }

    public void initView() {
        if(curWidth == 600 && curHeight == 400) {
            lineX = 410;
            panelOffset = 200;
            size = 17;
        }
        if(curWidth == 900 && curHeight == 600){
            lineX = 610;
            panelOffset =300;
            size = 25;

        }
        if(curWidth == 1200 && curHeight == 800) {
            lineX = 910;
            panelOffset =500;
            size = 30;

        }

        bufferSize = (int)(size/1.5);
        pane = new Pane();
        scene = new Scene(pane,1200, 800);
        pane.setStyle("-fx-background-color: #f3f3f3;");
        stage = new Stage();
        keyController.gameProc(scene);

        for (StackPane[] sp: rect1)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: rect2)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: nextRect1)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: nextRect2)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: buffer1)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: buffer2)
            Arrays.fill(sp, new StackPane());
    }

    public void setScene() {
        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(150);
        pauseButton.setLayoutX(curWidth/2-25);
        pauseButton.setPrefWidth(50);
        pauseButton.setPrefHeight(25);
        pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(
                event->{
                    controller.togglePause();
                }
        );
        pane.getChildren().addAll(pauseButton);

        //p1 mesh
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x*size + 10);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rect1[y][x] = sp;
                }
            }
        });

        //p2 mesh
        int start_pos = curWidth - size*WIDTH - size - 5;
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {

                    Rectangle r = new Rectangle(size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(start_pos + x*size);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rect2[y][x] = sp;
                }
            }
        });

        // p1 다음블럭
        Platform.runLater(() ->  {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Rectangle r = new Rectangle(size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(size*WIDTH + 20 + size*x);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    nextRect1[y][x] = sp;
                }
            }
        });

        // p2 다음블럭
        Platform.runLater(() ->  {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Rectangle r = new Rectangle(size, size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(start_pos - 10 - (4-x)*size);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    nextRect2[y][x] = sp;
                }
            }
        });

        // 버퍼1
        Platform.runLater(() ->  {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    Rectangle r = new Rectangle(bufferSize, bufferSize);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    StackPane sp = new StackPane();
                    sp.setLayoutX(size*WIDTH + 20 + bufferSize*x);
                    sp.setLayoutY(y*bufferSize + 200);
                    sp.getChildren().add(r);
                    pane.getChildren().add(sp);
                    buffer1[y][x] = sp;
                }
            }
        });

        // 버퍼2
        int start_pos_buffer = start_pos - bufferSize*10;
        Platform.runLater(() ->  {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    Rectangle r = new Rectangle(bufferSize, bufferSize);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    StackPane sp = new StackPane();
                    sp.setLayoutX(start_pos_buffer + bufferSize*x - 10);
                    sp.setLayoutY(y*bufferSize + 200);
                    sp.getChildren().add(r);
                    pane.getChildren().add(sp);
                    buffer2[y][x] = sp;
                }
            }
        });

        instance.getPrimaryStage().setScene(scene);
        instance.getPrimaryStage().setTitle("TETRIS");
        keyController.gameProc(scene);
        instance.getPrimaryStage().show();
    }

    public void color_mesh(int player) {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = (Rectangle)rect[player][y][x].getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(model.MESH[player][y][x], player));
                    Text t = (Text)rect[player][y][x].getChildren().get(1);
                    if (model.MESH[player][y][x] == 'L'){
                        t.setText("L");
                    }
                    else if (model.MESH[player][y][x] == 'b'){
                        t.setText("b");
                    }
                    else if (model.MESH[player][y][x] == 'V'){
                        t.setText("V");
                    }
                    else if (model.MESH[player][y][x] == 'B'){
                        t.setText("B");
                    }
                    else if (model.MESH[player][y][x] == 'g'){
                        t.setText("G");
                    }
                    else{
                        t.setText(" ");
                    }
                }
            }
        });

        //다음블럭 그리기
        Platform.runLater(
                () -> {
                    TetrominoBase next = (TetrominoBase) model.bags[player].get(1);
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            Rectangle r1 = (Rectangle) nextRect[player][y][x].getChildren().get(0);
                            if (next.mesh[y][x] == 0) {
                                r1.setFill(Color.WHITE);
                            } else {
                                r1.setFill(TetrominoBase.getColor(((TetrominoBase) model.bags[player].get(1)).name, -1));
                            }
                            Text t1 = (Text) nextRect[player][y][x].getChildren().get(1);
                            if (next.mesh[y][x] == 2) {
                                t1.setText("L");
                            } else if (next.mesh[y][x] == 3) {
                                t1.setText("b");
                            } else if (next.mesh[y][x] == 4) {
                                t1.setText("V");
                            } else if (next.mesh[y][x] == 5) {
                                t1.setText("B");
                            } else if (next.mesh[y][x] == 6) {
                                t1.setText("G");
                            } else {
                                t1.setText(" ");
                            }
                        }
                    }
                }
        );

        int enemy = (player == 1) ? 0 : 1;
        Platform.runLater(
                () -> {
                    for (int y = 0; y < 10; y++) {
                        for (int x = 0; x < 10; x++) {
                            Rectangle r1 = (Rectangle) buffer[enemy][y][x].getChildren().get(0);
                            if (model.buffer[enemy][y][x] == '0') {
                                r1.setFill(Color.WHITE);
                            } else {
                                r1.setFill(Color.GRAY);
                            }
                        }
                    }
                }
        );
    }
}
