package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.GameManager;
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

import java.io.IOException;
import java.util.Arrays;

import static com.snust.tetrij.Controller.ResolutionManager.curHeight;
import static com.snust.tetrij.Controller.ResolutionManager.curWidth;
import static com.snust.tetrij.GameScene.GameKeyController.addListenerPause;
import static com.snust.tetrij.GameSceneMulti.MultiKeyController.keyController;

public class MultiTetrisView {
    private final static GameManager instance = GameManager.getInstance();
    public final static MultiTetrisView view = new MultiTetrisView();

    public Scene scene;
    private static Pane pane;
    private Stage stage;

    private StackPane[][] rect1 = new StackPane[20][10];
    private StackPane[][] rect2 = new StackPane[20][10];
    private StackPane[][][] rect = new StackPane[][][] {rect1, rect2};

    private final int WIDTH = 10;
    private final int  HEIGHT = 20;
    private int size = 30;
    private int xmax;
    private int ymax;
    private int offset = 820;

    private int panelOffset = 500;

    private int lineX = 810;
    private boolean isPaused;
    private boolean onPauseButton;

    private MultiTetrisView() {

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
        scene = new Scene(pane,1200, 800);
        pane.setStyle("-fx-background-color: #f3f3f3;");
        stage = new Stage();
        addListenerPause(scene);

        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(150);
        pauseButton.setLayoutX(xmax + 5 + offset);
        pauseButton.setPrefWidth(50);
        pauseButton.setPrefHeight(25);
        pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
        pauseButton.setFocusTraversable(false);

        Line line = new Line(xmax+ offset,0,xmax + offset, ymax + offset);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(xmax + 5 + offset);
        Text lines = new Text("Lines: ");
        lines.setStyle("-fx-font: 20 arial;");
        lines.setY(100);
        lines.setX(xmax + 5 + offset);
        lines.setFill(Color.GREEN);

//        Text keyText = new Text("왼쪽 이동: "+ MultiTetrisController.controller.leftKeyCode+"\n오른쪽 이동: "+ MultiTetrisController.controller.rightKeyCode
//                + "\n아래 이동: "+ MultiTetrisController.controller.downKeyCode + "\n회전: "+ MultiTetrisController.controller.rotateKeyCode
//                + "\n드롭 버튼: "+ MultiTetrisController.controller.dropKeyCode);
//        keyText.setStyle("-fx-font: 10 arial;");
//        keyText.setY(300 + offset/5);
//        keyText.setX(xmax + 5 + offset);
//        pane.getChildren().addAll(scoretext, line, lines, pauseButton);

        Line line2 = new Line(lineX,0,lineX, ymax + offset);
        Text scoretext2 = new Text("Score: ");
        scoretext2.setStyle("-fx-font: 20 arial;");
        scoretext2.setY(50);
        scoretext2.setX(lineX+5);
        Text lines2 = new Text("Lines: ");
        lines2.setStyle("-fx-font: 20 arial;");
        lines2.setY(100);
        lines2.setX(lineX+5);
        lines2.setFill(Color.GREEN);

//        Button pauseButton2 = new Button("Pause");
//        pauseButton2.setLayoutY(150);
//        pauseButton2.setLayoutX(xmax + 5 + offset);
//        pauseButton2.setPrefWidth(50);
//        pauseButton2.setPrefHeight(25);
//        pauseButton2.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
//        pauseButton2.setFocusTraversable(false);
        pane.getChildren().addAll(scoretext2, pauseButton, line2, lines2);


        for (StackPane[] sp: rect1)
            Arrays.fill(sp, new StackPane());

        for (StackPane[] sp: rect2)
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
                    rect1[y][x] = sp;
                }
            }
        });

        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(x* size, y*size, size, size);
                    r.setFill(Color.BLACK);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x*size + panelOffset);
                    sp.setLayoutY(y*size);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rect2[y][x] = sp;
                }
            }
        });

        instance.getPrimaryStage().setScene(scene);
        instance.getPrimaryStage().setTitle("TETRIS");
        keyController.gameProc(scene);
        instance.getPrimaryStage().show();
    }

    public void togglePause() {
        if(!onPauseButton){
            isPaused = !isPaused;
            if (isPaused) {
                // Pause 버튼을 눌렀을 때 퍼즈 메뉴 창 띄우기
                try {
                    onPauseButton = true; // 퍼즈 버튼 눌러서 true (퍼즈 창이 떠 있는 상태)
                    FXMLLoader fxmlLoader = new FXMLLoader(Tetris.class.getResource("pause_menu.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage pauseStage = new Stage();
                    pauseStage.setScene(new Scene(root));
                    pauseStage.setTitle("Pause");
                    pauseStage.setOnCloseRequest(event -> {
                        // Pause 창이 닫힐 때 isPaused와 onPauseButton을 false로 변경
                        isPaused = false; //퍼즈 해제
                        onPauseButton = false; // 창 꺼짐
                    });
                    pauseStage.getScene().setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.ESCAPE) {
                            pauseStage.close();
                            Platform.exit();
                        }
                    });
                    pauseStage.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void color_mesh(int player) {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = (Rectangle)rect[player][y][x].getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(MultiTetrisModel.model.MESH[player][y][x]));
                    Text t = (Text)rect[player][y][x].getChildren().get(1);
                    if (MultiTetrisModel.model.MESH[player][y][x] == 'L'){
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
