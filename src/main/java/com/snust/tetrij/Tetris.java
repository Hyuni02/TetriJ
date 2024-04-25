package com.snust.tetrij;

import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static com.snust.tetrij.GameOverController.switchToGameOver;

public class Tetris extends Application {
    // consts for game
    public static final int SIZE = 20;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * 20;
    public static final int WIDTH = XMAX/SIZE;
    public static final int HEIGHT = YMAX/SIZE;
    public static int top = 0;

    // fx items
    public static Pane pane = new Pane();
    private static Scene scene = new Scene(pane, XMAX + 150, YMAX);
    public static char [][] MESH = new char[HEIGHT][WIDTH];
    private static Pane group = new Pane();
    private static String screenSize;

    // variables for game
    public static Thread thread;
    public static boolean item_mode = false;
    public static boolean restart = false;
    public static boolean isGameOver = false;
    public enum difficulty {EASY, NORMAL, HARD};
    public static boolean color_weakness = false;
    public static int score = 0; //점수
    public static boolean game = true;
    public static int linesNo = 0; //지워진 줄 수
    public static boolean isZeroScoreText = true;
    private static int freq = 300; //하강 속도
    public static int speedLevel = 0; //지워진 줄 수에 따른 속도 레벨
    private static int boost = 30; //하강 속도 증가량
    public static int deleted_lines = 0;
    private MediaPlayer mediaPlayer;
    // 퍼즈 관련 변수
    protected static boolean isPaused = false; // 퍼즈 중인가?
    protected static boolean onPauseButton = false; // 퍼즈 버튼을 눌러서 퍼즈 창이 떠 있는 상태인가?
    // region 블럭 움직임 키 설정
    static String rightKey = loadKeySetting("right");
    static String leftKey = loadKeySetting("left");
    static String rotateKey = loadKeySetting("rotate");
    static String downKey = loadKeySetting("down");
    static String dropKey = loadKeySetting("drop");
    static KeyCode rightKeyCode = getKeyCodeFromString(rightKey);
    static KeyCode leftKeyCode = getKeyCodeFromString(leftKey);
    static KeyCode rotateKeyCode = getKeyCodeFromString(rotateKey);
    static KeyCode downKeyCode = getKeyCodeFromString(downKey);
    static KeyCode dropKeyCode = getKeyCodeFromString(dropKey);
    // endregion


    @Override
    public void start(Stage stage) throws Exception{
        newGameScene(stage, difficulty.EASY);
    }

    public static void newGameScene(Stage stage, difficulty dif) throws IOException {
        loadSettings();
        System.out.println(dif.toString());
        Controller.SetField(dif);

        if(restart) {
            //pane.getChildren().clear(); // 현재 씬 모든 노드 제거
            Controller.bag.clear();

            //thread.interrupt(); // 스레드 중지

            // 변수 초기화
            score = 0;
            top = 0;
            linesNo = 0;
            game = true;
            isPaused = false; // 퍼즈 후 시작화면으로 나가서 재시작할때 오류방지
//            fall.cancel(); // 타이머 리셋
//            fall.purge();
        }
//        if(!restart)
//        fall = new Timer(); // 타이머 전역변수로 뺌 -> 리셋 가능


        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() { // 키 이벤트
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.P) {
                    togglePause(); // P 누르면 퍼즈
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.exit(0); // ESC 누르면 창 닫기
                }
            }
        });

        for(char[] a:MESH){
            Arrays.fill(a, '0');
        }

        Line line = new Line(XMAX,0,XMAX,YMAX);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arial;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        //pane.getChildren().addAll(scoretext, line, level);

        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutY(150);
        pauseButton.setLayoutX(XMAX + 5);
        pauseButton.setPrefWidth(50);
        pauseButton.setPrefHeight(25);
        pauseButton.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; fx-font-size: 20px;");
        pauseButton.setFocusTraversable(false);

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Rectangle r = new Rectangle(XMAX+10+x*Tetris.SIZE, 200+y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                r.setFill(Color.WHITE);
                pane.getChildren().add(r);
            }
        }

        Text keyText = new Text("왼쪽 이동: "+leftKeyCode+"\n오른쪽 이동: "+rightKeyCode + "\n아래 이동: "+downKeyCode + "\n회전: "+rotateKeyCode + "\n드롭 버튼: "+dropKeyCode);
        keyText.setStyle("-fx-font: 10 arial;");
        keyText.setY(300);
        keyText.setX(XMAX + 5);

        pane.getChildren().addAll(scoretext, line, level, pauseButton, keyText);

        pauseButton.setOnAction(event -> togglePause());

        //generate first block
        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();

        //set listener
        scene.setOnKeyPressed(e->{
            javafx.scene.input.KeyCode code = e.getCode();
            if (Controller.bag.isEmpty())
                code = KeyCode.NONCONVERT;

            if(code == KeyCode.NONCONVERT);
            else if(code == KeyCode.ESCAPE){
                System.out.println("esc");
                game = !game;
            }
            else if(code == leftKeyCode){
                Controller.moveLeftOnKeyPress(Controller.bag.get(0));
                color_mesh();
            }
            else if(code == rightKeyCode){
                Controller.moveRightOnKeyPress(Controller.bag.get(0));
                color_mesh();
            }
            else if(code == rotateKeyCode){
                Controller.rotateRight(Controller.bag.get(0));
                color_mesh();
            }
            else if(code == downKeyCode){
                Controller.softDrop(Controller.bag.get(0));
                color_mesh();
            }
            else if(code == dropKeyCode){
                Controller.hardDrop(Controller.bag.get(0));
                color_mesh();
            }
        });

        Controller.generateTetromino();
        Controller.generateTetromino();
        Controller.bag.get(0).update_mesh();
        color_mesh();

        if(!restart) { // 처음 한번만
            //runtime logic
            Runnable task = new Runnable() {
                public void run() {
                    while (!isGameOver) {
                        try {
                            int finalFreq = 0;
                            switch (dif) {
                                case EASY -> finalFreq = freq - speedLevel * (int) (boost * 0.8f);
                                case NORMAL -> finalFreq = freq - speedLevel * boost;
                                case HARD -> finalFreq = freq - speedLevel * (int) (boost * 1.2f);
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

                        //일시정지
                        if (isPaused) continue;

                        //game running

/*                        for (char[] arr : MESH) {
                            System.out.println(arr);
                        }
                        System.out.println("\n");*/

                        if (Controller.bag.size() >= 2) {
                            Controller.softDrop(Controller.bag.get(0));
                        }
                        if (Controller.bag.size() < 2) {
                            Controller.generateTetromino();
                            Controller.bag.get(0).update_mesh();
                        }
                        color_mesh();

                        //다음블럭 그리기
                        Platform.runLater(
                                ()->{
                                    TetrominoBase next = Controller.bag.get(1);
                                    int nextWidth = next.getWidth();
                                    int nextHeight = next.getHeight();
                                    for (int y = 0; y < 4; y++) {
                                        for (int x = 0; x < 4; x++) {
                                            Rectangle r = new Rectangle(XMAX+10+x*Tetris.SIZE, 200+y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                                            if (x >= nextWidth || y >= nextHeight) {
                                                r.setFill(Color.WHITE);
                                            }
                                            else if (next.mesh[next.rotate][y][x] == 0) {
                                                r.setFill(Color.WHITE);
                                            }
                                            else {
                                                r.setFill(TetrominoBase.getColor(Controller.bag.get(1).name));
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
        restart = true;
    }

    @FXML
    public static void GameOver(Stage stage, difficulty dif){
        Platform.runLater(()-> {
            switchToGameOver(score, stage, dif);
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

    private static String loadKeySetting(String key) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("keysetting.json")), "UTF-8");
            JSONObject settings = new JSONObject(content);
            return settings.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static KeyCode getKeyCodeFromString(String keyName) {    //json -> KeyCode로 변경
        for (KeyCode kc : KeyCode.values()) {
            if (kc.getName().equalsIgnoreCase(keyName)) {
                return kc;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }

    private static void color_mesh() {
        Platform.runLater(() ->  {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Rectangle r = new Rectangle(x*Tetris.SIZE, y*Tetris.SIZE, Tetris.SIZE, Tetris.SIZE);
                    r.setFill(TetrominoBase.getColor(MESH[y][x]));
                    r.setStrokeWidth(0.5);
                    r.setStroke(Color.BLACK);
                    pane.getChildren().add(r);
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
            File file = new File("setting.json");
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            fileReader.close();

            JSONObject setting = new JSONObject(stringBuilder.toString());
            screenSize = setting.getString("screenSize");
            color_weakness = setting.getBoolean("isColorBlind");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToStartMenu() throws IOException { // 초기화면으로 돌아감
        isPaused = true;
        FXMLLoader loader = new FXMLLoader(InGame.class.getResource("start_menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) scene.getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        com.snust.tetrij.SetResolution.setResolution(root, (int) stage.getHeight(), (int) stage.getWidth());
    }

    @FXML
    private void exitGame() {
        ClickButtonSound();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게임 종료");
        alert.setHeaderText("게임을 종료하시겠습니까?");
        alert.setContentText("게임을 종료하려면 확인을 누르세요.");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    private void ClickButtonSound() {
        try {
            Media sound = new Media(new File("src/main/resources/com/snust/tetrij/sound/button_click.mp3").toURI().toString());

            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
