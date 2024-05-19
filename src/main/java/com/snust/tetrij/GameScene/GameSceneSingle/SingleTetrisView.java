package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameSceneSingle.Control.TetrisBoardController;
import com.snust.tetrij.Tetris;
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
import org.w3c.dom.css.Rect;

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

    public final int WIDTH = 10;
    public final int  HEIGHT = 20;
    private final int size = 30;
    private int xmax;
    private int ymax;
    private final int offset = 320;

    public Text scoreText;
    public Text level;

    private SingleTetrisView() {
        pane = new Pane();
        scene = new Scene(pane,1200, 800);
        stage = new Stage();

        scoreText = new Text();
        level = new Text();

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
        pauseButton.setOnAction(
                e -> {
                    if(!controller_s.onPauseButton){
                        controller_s.isPaused = !controller_s.isPaused;
                        if (controller_s.isPaused) {
                            // Pause 버튼을 눌렀을 때 퍼즈 메뉴 창 띄우기
                            try {
                                controller_s.onPauseButton = true; // 퍼즈 버튼 눌러서 true (퍼즈 창이 떠 있는 상태)
                                FXMLLoader fxmlLoader = new FXMLLoader(Tetris.class.getResource("pause_menu.fxml"));
                                Parent root = fxmlLoader.load();
                                Stage pauseStage = new Stage();
                                pauseStage.setScene(new Scene(root));
                                pauseStage.setTitle("Pause");
                                pauseStage.setOnCloseRequest(event -> {
                                    // Pause 창이 닫힐 때 isPaused와 onPauseButton을 false로 변경
                                    controller_s.isPaused = false; //퍼즈 해제
                                    controller_s.onPauseButton = false; // 창 꺼짐
                                });
                                pauseStage.getScene().setOnKeyPressed(event -> {
                                    if (event.getCode() == KeyCode.ESCAPE) {
                                        pauseStage.close();
                                        Platform.exit();
                                    }
                                });
                                pauseStage.showAndWait();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }
        );

//        Text keyText = new Text("왼쪽 이동: "+ MultiTetrisController.controller.leftKeyCode+"\n오른쪽 이동: "+ MultiTetrisController.controller.rightKeyCode
//                + "\n아래 이동: "+ MultiTetrisController.controller.downKeyCode + "\n회전: "+ MultiTetrisController.controller.rotateKeyCode
//                + "\n드롭 버튼: "+ MultiTetrisController.controller.dropKeyCode);
//        keyText.setStyle("-fx-font: 10 arial;");
//        keyText.setY(300 + offset/5);
//        keyText.setX(xmax + 5 + offset);
//        pane.getChildren().addAll(scoretext, line, lines, pauseButton);

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

        for (StackPane[] sp: rect)
            Arrays.fill(sp, new StackPane());
    }

    public void setScene() {
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
                    rect[y][x] = sp;
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
                    TetrominoBase next = TetrisBoardController.bag.get(1);
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            Rectangle r = new Rectangle(200 + 10 + x * view_s.size + offset, 200 + y * view_s.size, view_s.size, view_s.size);
                            if (next.mesh[y][x] == 0) {
                                r.setFill(Color.WHITE);
                            } else {
                                r.setFill(TetrominoBase.getColor(TetrisBoardController.bag.get(1).name));
                            }
                            pane.getChildren().add(r);
                        }
                    }
                }
        );
    }
}
