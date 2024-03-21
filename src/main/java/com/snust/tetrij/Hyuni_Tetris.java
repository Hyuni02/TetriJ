package com.snust.tetrij;

import com.almasb.fxgl.net.DownloadCallback;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Hyuni_Tetris extends Application {

    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static final int XMAX = SIZE * 12;
    public static final int YMAX = SIZE * 24;
    public static final int [][] MESH = new int [XMAX/SIZE][YMAX/SIZE];
    private static Pane groupe = new Pane();
    private static Hyuni_Form object;
    private static Scene scene = new Scene(groupe, XMAX + 150, YMAX);
    public static int score = 0;
    public static int top = 0;
    private static boolean game = true;
    private static Hyuni_Form nextObj = Hyuni_Controller.makeRect();
    private static int linesNo = 0;

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
        Hyuni_Form a = nextObj;
        groupe.getChildren().addAll(a.a,a.b,a.c,a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Hyuni_Controller.makeRect();
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();

        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                        public void run(){
                            if(object.a.getY()==0
                                    || object.b.getY()==0
                            || object.c.getY()==0
                            || object.d.getY()==0){
                                top++;
                            }
                            else{
                                top =0;
                            }

                            if(top == 2){
                                Text over = new Text("Game Over");
                                over.setFill(Color.RED);
                                over.setStyle("-fx-font: 70 arial;");
                                over.setY(250);
                                over.setX(10);
                                groupe.getChildren().add(over);
                                game = false;
                            }

                            if(top == 15){
                                System.exit(0);
                            }

                            if(game){
                                MoveDown(object);
                                scoretext.setText("Score: " + Integer.toString(Screen));
                                level.setText("Lines: " + Integer.toString(linesNo));
                            }
                    }
                });
            }
        };
        fall.schedule(task,0,300);
    }

    private void moveOnKeyPress(Hyuni_Form form){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){
                    case RIGHT:
                        Hyuni_Controller.MoveRight(form);
                        break;
                    case DOWN:
                        MoveDown(form);
                        break;
                    case LEFT:
                        Hyuni_Controller.MoveLeft(form);
                        break;
                    case UP:
                        MoveTurn(form);
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
