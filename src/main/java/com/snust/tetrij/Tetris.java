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
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends Application {
    // consts for game
    public static final int SIZE = 20;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * 20;
    public static final int WIDTH = XMAX/SIZE;
    public static final int HEIGHT = YMAX/SIZE;
    public static int top = YMAX;

    // fx items
    public static Pane pane = new Pane();
    private static Scene scene = new Scene(pane, XMAX + 150, YMAX);
    public static char [][] MESH = new char[HEIGHT][WIDTH];
    private static Pane group = new Pane();

    // variables for game
    public static boolean restart = false;
    public enum difficulty {EASY, NORMAL, HARD};
    public static boolean color_weakness = true;
    public static int score = 0;
    public static boolean game = true;
    private static int linesNo = 0;
    private static Timer fall;
    private MediaPlayer mediaPlayer;
    // 퍼즈 관련 변수
    protected static boolean isPaused = false; // 퍼즈 중인가?
    protected static boolean onPauseButton = false; // 퍼즈 버튼을 눌러서 퍼즈 창이 떠 있는 상태인가?
    //블럭 움직임 키 설정
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

    @Override
    public void start(Stage stage) throws Exception{
        newGameScene(stage);
    }

    public static void newGameScene(Stage stage) throws IOException {
        if(restart) {
            group.getChildren().clear(); // 현재 씬 모든 노드 제거

            // 변수 초기화
            score = 0;
            top = 0;
            linesNo = 0;
            game = true;
            isPaused = false; // 퍼즈 후 시작화면으로 나가서 재시작할때 오류방지
        }
        if (restart) {
            fall.cancel(); // 타이머 리셋
        }
        fall = new Timer(); // 타이머 전역변수로 뺌 -> 리셋 가능
        restart = true;

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
        scoretext.setStyle("-fx-font: 20 arials;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arials;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        pane.getChildren().addAll(scoretext, line, level);

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

//            switch (code) {
//                case SPACE -> {
//                    Controller.hardDrop(Controller.bag.get(0));
//                    color_mesh();
//                }
//                case RIGHT -> {
//                    Controller.moveRightOnKeyPress(Controller.bag.get(0));
//                    color_mesh();
//                }
//                case LEFT -> {
//                    Controller.moveLeftOnKeyPress(Controller.bag.get(0));
//                    color_mesh();
//                }
//                case UP -> {
//                    Controller.rotateRight(Controller.bag.get(0));
//                    color_mesh();
//                }
//                case DOWN -> {
//                    Controller.softDrop(Controller.bag.get(0));
//                    color_mesh();
//                }
//                case ESCAPE -> {
//                    System.out.println("esc");
//                    game = !game;
//                }
//                default -> {
//
//                }
//            }
        });
        Controller.generateTetromino();
        Timer timer = new Timer();
        color_mesh();

        //runtime logic
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //일시정지
                if(isPaused) return;

                //게임 오버 띄우기
                
                //점수 입력창 띄우기

                //game running
                if (!Controller.bag.isEmpty())
                    Controller.softDrop(Controller.bag.get(0));
                else
                    Controller.generateTetromino();
                color_mesh();
            }
        };
        timer.schedule(task, 1000, 1000);
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

    public static void switchToStartMenu() throws IOException { // 초기화면으로 돌아감
        isPaused = true;
        FXMLLoader loader = new FXMLLoader(InGame.class.getResource("start_menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) scene.getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
