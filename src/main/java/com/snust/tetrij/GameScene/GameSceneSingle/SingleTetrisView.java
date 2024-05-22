package com.snust.tetrij.GameScene.GameSceneSingle;

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

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiKeyController.keyController;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;

public class SingleTetrisView {
    public final static SingleTetrisView view_s = new SingleTetrisView();

    public Scene scene;
    private static Pane pane;
    public Stage stage;

    private StackPane[][] rect = new StackPane[20][10];
    public Rectangle[][] rectMesh = new Rectangle[20][10];
    private StackPane[][] nextRect = new StackPane[4][4];

    public final int WIDTH = 10;
    public final int  HEIGHT = 20;
    private final int size = 30;
    private final int offset = 320;

    public Text scoretext;
    public Text level;
    public Text lines;

    private SingleTetrisView() {
        pane = new Pane();
        scene = new Scene(pane,500, 800);
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
            Line line = new Line(offset,0, offset, offset);
            scoretext = new Text("Score: ");
            scoretext.setStyle("-fx-font: 20 arial;");
            scoretext.setY(50);
            scoretext.setX(5 + offset);
            lines = new Text("Lines: ");
            lines.setStyle("-fx-font: 20 arial;");
            lines.setY(100);
            lines.setX(5 + offset);
            lines.setFill(Color.GREEN);

            Button pauseButton = new Button("Pause");
            pauseButton.setLayoutY(150);
            pauseButton.setLayoutX(5 + offset);
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
                    Rectangle r = new Rectangle(x * view_s.size + offset, 200 + y * view_s.size, view_s.size, view_s.size);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x * view_s.size + offset);
                    sp.setLayoutY(200 + y * view_s.size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    nextRect[y][x] = sp;
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("TETRIS");
        keyController.gameProc(scene);
        stage.show();
    }

    public void color_mesh() {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = (Rectangle)rect[y][x].getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(model_s.MESH[y][x]));
                    Text t = (Text)rect[y][x].getChildren().get(1);
                    if (model_s.MESH[y][x] == 'L'){
                        t.setText("L");
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
                    TetrominoBase next = SingleBoardController.bag.get(1);
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            Rectangle r = (Rectangle) nextRect[y][x].getChildren().get(0);
                            if (next.mesh[y][x] == 0) {
                                r.setFill(Color.WHITE);
                            } else {
                                r.setFill(TetrominoBase.getColor(SingleBoardController.bag.get(1).name));
                            }
                            Text t = (Text)rect[y][x].getChildren().get(1);
                            if (model_s.MESH[y][x] == 'L'){
                                t.setText("L");
                            }
                            else{
                                t.setText(" ");
                            }
                        }
                    }
                }
        );
    }
}
