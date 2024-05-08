package com.snust.tetrij;


import com.snust.tetrij.GameScene.TetrisBoardController;
import com.snust.tetrij.GameScene.tetromino.TetrominoBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
import org.json.JSONObject;

import java.io.*;
import java.util.Arrays;

import static com.snust.tetrij.Controller.ResolutionManager.curHeight;
import static com.snust.tetrij.Controller.ResolutionManager.curWidth;

import static com.snust.tetrij.GameScene.GameKeyController.*;
import static com.snust.tetrij.Controller.GameOverController.switchToGameOver;

public class Tetris extends Application {
    public static double offset = 500;
    // consts for game
    public static int SIZE = 30;
    public static final int XMAX = 20 * 10;
    public static final int YMAX = 20 * 20;
    public static final int WIDTH = XMAX/20;
    public static final int HEIGHT = YMAX/20;
    public static int top = 0;

    // fx items
    private static Pane pane = new Pane(); // 기존 Pane 사용
    private static Scene scene = new Scene(pane, XMAX + 150, YMAX);
    public static char [][] MESH = new char[HEIGHT][WIDTH];
    public static Rectangle[][] rectMesh = new Rectangle[HEIGHT][WIDTH];    //애니메이션용..
    public static int childrens_without_blocks;

    // variables for game
    public enum difficulty {EASY, NORMAL, HARD, ITEM};
    public static boolean restart = false;
    public static boolean isGameOver = false;
    public static difficulty cur_dif;
    public static boolean color_weakness = false;
    public static int score = 0; //점수
    public static boolean game = true;
    public static int linesNo = 0; //지워진 줄 수
    public static int speedLevel = 0; //지워진 줄 수에 따른 속도 레벨
    public static int deleted_lines = 0;
    // 퍼즈 관련 변수
    public static boolean isPaused = false; // 퍼즈 중인가?
    protected static boolean onPauseButton = false; // 퍼즈 버튼을 눌러서 퍼즈 창이 떠 있는 상태인가?
    // endregion
    public Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        pane = new Pane();  // Pane 재정의

        scene = new Scene(pane, 1200, 800); // 기본 해상도 설정
        stage.setScene(scene);  // Stage에 Scene 설정

        stage.show();
        newGameScene(stage, Tetris.difficulty.EASY);  // 새 게임 시작
    }

    public static void main(String[] args) {
        launch();
    }

    public static void newGameScene(Stage stage, difficulty dif) throws IOException {
        if(curWidth == 600 && curHeight == 400) {
            SIZE = 18;
            offset = 0;
        }
        if(curWidth == 900 && curHeight == 600){
            SIZE = 25;
            offset = 300;
        }
        if(curWidth == 1200 && curHeight == 800){
            SIZE = 30;
            offset = 500;
        }
        cur_dif = dif;
        loadSettings();
        System.out.println(dif.toString());
        TetrisBoardController.RWS(dif);


        pane.getChildren().clear(); // 현재 씬 모든 노드 제거
        TetrisBoardController.bag.clear();
        // 변수 초기화
        score = 0;
        top = 0;
        linesNo = 0;
        game = true;
        isPaused = false; // 퍼즈 후 시작화면으로 나가서 재시작할때 오류방지
        isGameOver = false;
        childrens_without_blocks = 0;

        addListener(scene); //GameScene.GameKeyController.addListener

        for(char[] a:MESH){
            Arrays.fill(a, '0');
        }

        Line line = new Line(XMAX+ offset,0,XMAX + offset,YMAX + + offset);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5 + offset);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arial;");
        level.setY(100);
        level.setX(XMAX + 5 + offset);
        level.setFill(Color.GREEN);

        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(150);
        pauseButton.setLayoutX(XMAX + 5 + offset);
        pauseButton.setPrefWidth(50);
        pauseButton.setPrefHeight(25);
        pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
        pauseButton.setFocusTraversable(false);

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Rectangle r = new Rectangle(XMAX+10+x*Tetris.SIZE + offset, 200+y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                r.setFill(Color.WHITE);
                pane.getChildren().add(r);
            }
        }

        Text keyText = new Text("왼쪽 이동: "+leftKeyCode+"\n오른쪽 이동: "+rightKeyCode
                + "\n아래 이동: "+downKeyCode + "\n회전: "+rotateKeyCode + "\n드롭 버튼: "+dropKeyCode);
        keyText.setStyle("-fx-font: 10 arial;");
        keyText.setY(300 + offset/5);
        keyText.setX(XMAX + 5 + offset);

        pane.getChildren().addAll(scoretext, line, level, pauseButton, keyText);
        pauseButton.setOnAction(event -> togglePause());

        //generate first block
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();
        childrens_without_blocks = init_mesh();
        gameProc(scene); //GameScene.GameKeyController.gameProc - setting event listener

        TetrisBoardController.generateTetromino();
        TetrisBoardController.generateTetromino();
        TetrisBoardController.bag.get(0).update_mesh();
        color_mesh(childrens_without_blocks);

        Thread thread;
        Runnable task = new Runnable() {
            public void run() {
                while (!isGameOver) {
                    int freq = 300;
                    try {
                        //일시정지
                        if (isPaused) {
                            System.out.print("");   //이거없음 pauseButton 버그남 ㄷㄷ
                            continue;
                        }

                        int finalFreq = 0;
                        int boost = 30;
                        switch (dif) {
                            case EASY -> finalFreq = freq - speedLevel * (int) (boost * 0.8f);
                            case HARD -> finalFreq = freq - speedLevel * (int) (boost * 1.2f);
                            default -> finalFreq = freq - speedLevel * boost;
                        }
                        Thread.sleep(finalFreq);

                        if (speedLevel == 0)
                            score++;
                        else if (speedLevel == 1)
                            score += 2;
                        else if (speedLevel == 2)
                            score += 3;

                        scoretext.setText("Score: " + Integer.toString(score));
                        level.setText("Lines: " + Integer.toString(linesNo));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    TetrisBoardController.softDrop(TetrisBoardController.bag.get(0));
                    color_mesh(childrens_without_blocks);

                    //다음블럭 그리기
                    Platform.runLater(
                            () -> {
                                TetrominoBase next = TetrisBoardController.bag.get(1);
                                for (int y = 0; y < 4; y++) {
                                    for (int x = 0; x < 4; x++) {
                                        Rectangle r = new Rectangle(XMAX + 10 + x * Tetris.SIZE + offset, 200 + y * Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
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
                    //게임오바
                    if (Tetris.top >= Tetris.HEIGHT - 1) {
                        System.out.println("game over");
                        isGameOver = true;
                    }
                }
                GameOver(stage, dif);
            }
        };
        thread = new Thread(task);
        thread.start();
    }

    @FXML
    public static void GameOver(Stage stage, difficulty dif){
        Platform.runLater(()-> {
            switchToGameOver(score, dif);
        });
    }

    public static void changeSpeed(){
        if(linesNo <= 5){
            speedLevel = 0;
        }
        else if(linesNo <= 10){
            speedLevel = 1;
        }
        else {
            speedLevel = 2;
        }
    }

    public static int init_mesh() {
        int childrens_witout_blocks = pane.getChildren().size();
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(x*Tetris.SIZE, y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                    r.setFill(Color.WHITE);
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    Text t = new Text(" ");
                    StackPane sp = new StackPane();
                    sp.setLayoutX(x*Tetris.SIZE);
                    sp.setLayoutY(y*Tetris.SIZE);
                    sp.getChildren().addAll(r, t);
                    pane.getChildren().add(sp);
                    rectMesh[y][x] = r; //애니메이션용..
                }
            }
        });
        return childrens_witout_blocks;
    }

    public static void color_mesh(int start_pos) {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    StackPane sp = (StackPane)pane.getChildren().get(start_pos+y*WIDTH+x);
                    Rectangle r = (Rectangle)sp.getChildren().get(0);
                    r.setFill(TetrominoBase.getColor(MESH[y][x]));
                    Text t = (Text)sp.getChildren().get(1);
                    if (MESH[y][x] == 'L'){
                        t.setText("L");
                    }
                    else{
                        t.setText(" ");
                    }
                }
            }
        });
    }

    public static void togglePause() {
        if(!onPauseButton){
            isPaused = !isPaused;
            if (isPaused) {
                // Pause 버튼을 눌렀을 때 퍼즈 메뉴 창 띄우기
                try {
                    onPauseButton = true; // 퍼즈 버튼 눌러서 true (퍼즈 창이 떠 있는 상태)
                    FXMLLoader fxmlLoader = new FXMLLoader(InGame.class.getResource("pause_menu.fxml"));
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

    private static void loadSettings() {    //셋팅 파일 읽어옴
        try {
            File file = new File("src/main/resources/com/snust/tetrij/setting.json");
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            fileReader.close();

            JSONObject setting = new JSONObject(stringBuilder.toString());
            String screenSize = setting.getString("screenSize");
            color_weakness = setting.getBoolean("isColorBlind");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
