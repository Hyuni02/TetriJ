package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameManager;
import com.snust.tetrij.SingleTetris;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
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
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;

public class SingleTetrisView {
    public final static GameManager instance = GameManager.getInstance();
    public final static SingleTetrisView view_s = new SingleTetrisView();

    public Scene scene;
    private static Pane pane;
    public Stage stage;

    public StackPane[][] rect = new StackPane[20][10];
    public Rectangle[][] rectMesh = new Rectangle[20][10];
    private StackPane[][] nextRect = new StackPane[4][4];

    public final int WIDTH = 10;
    public final int  HEIGHT = 20;
    private int size;
    private int lineX;
    private int panelOffset;

    public Text scoretext;
    public Text level;
    public Text lines;

    private SingleTetrisView() {
        initView();
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

        pane = new Pane();
        scene = new Scene(pane,curWidth, curHeight);
        stage = new Stage();

        scoretext = new Text();
        level = new Text();

        for (StackPane[] sp: rect)
            Arrays.fill(sp, new StackPane());
        for (StackPane[] sp: nextRect)
            Arrays.fill(sp, new StackPane());
    }

    public void setScene() {
        Platform.runLater(() ->  {
            Line line = new Line(panelOffset,0, panelOffset, panelOffset);
            scoretext = new Text("Score: ");
            scoretext.setStyle("-fx-font: 20 arial;");
            scoretext.setY(50);
            scoretext.setX(5 + panelOffset);
            lines = new Text("Lines: ");
            lines.setStyle("-fx-font: 20 arial;");
            lines.setY(100);
            lines.setX(5 + panelOffset);
            lines.setFill(Color.GREEN);

            Button pauseButton = new Button("Pause");
            pauseButton.setLayoutY(150);
            pauseButton.setLayoutX(5 + panelOffset);
            pauseButton.setPrefWidth(50);
            pauseButton.setPrefHeight(25);
            pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
            pauseButton.setFocusTraversable(false);
            pauseButton.setOnAction(
                    e -> controller_s.togglePause()
            );
            pane.getChildren().addAll(line, scoretext, lines, pauseButton);
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
                    rect[y][x] = sp;
                }
            }
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Rectangle r = new Rectangle(x * view_s.size + panelOffset, 200 + y * view_s.size, view_s.size, view_s.size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x * view_s.size + panelOffset);
                    sp.setLayoutY(200 + y * view_s.size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    nextRect[y][x] = sp;
                }
            }
        });

        instance.getPrimaryStage().setScene(scene);
        instance.getPrimaryStage().setTitle("TETRIS");
        keyController.gameProc(scene);
        instance.getPrimaryStage().show();
    }

    public void color_mesh() {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = (Rectangle)rect[y][x].getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(model_s.MESH[y][x], -1));
                    Text t = (Text)rect[y][x].getChildren().get(1);
                    if (model_s.MESH[y][x] == 'L'){
                        t.setText("L");
                    }
                    else if (model_s.MESH[y][x] == 'b'){
                        t.setText("b");
                    }
                    else if (model_s.MESH[y][x] == 'V'){
                        t.setText("V");
                    }
                    else if (model_s.MESH[y][x] == 'B'){
                        t.setText("B");
                    }
                    else {
                        t.setText(" ");
                    }
                }
            }
        });

        //다음블럭 그리기
        Platform.runLater(
                () -> {
                    TetrominoBase next = model_s.bag.get(1);
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            Rectangle r = (Rectangle) nextRect[y][x].getChildren().get(0);
                            if (next.mesh[y][x] == 0) {
                                r.setFill(Color.WHITE);
                            } else {
                                r.setFill(TetrominoBase.getColor(model_s.bag.get(1).name, -1));
                            }
                            Text t = (Text)rect[y][x].getChildren().get(1);
                            if (next.mesh[y][x] == '2'){
                                t.setText("L");
                            }
                            else if (next.mesh[y][x] == '3'){
                                t.setText("b");
                            }
                            else if (next.mesh[y][x] == '4'){
                                t.setText("V");
                            }
                            else if (next.mesh[y][x] == '5'){
                                t.setText("B");
                            }
                            else {
                                t.setText(" ");
                            }
                        }
                    }
                }
        );
    }
}
