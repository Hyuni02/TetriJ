package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.GameScene.tetromino.TetrominoBase;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.snust.tetrij.GameSceneMulti.MultiBoardController.*;

public class MultiTetrisController {
    public MultiTetrisModel model;
    public MultiTetrisView view;
    public final static MultiTetrisController controller = new MultiTetrisController();

    public String rightKey;
    public String leftKey;
    public String rotateKey;
    public String downKey;
    public String dropKey;
    public KeyCode rightKeyCode;
    public KeyCode leftKeyCode;
    public KeyCode rotateKeyCode;
    public KeyCode downKeyCode;
    public KeyCode dropKeyCode;

    public boolean isPaused = false;
    public boolean isGameOver = false;

    public MultiTetrisController() {
        model = new MultiTetrisModel();
        view = new MultiTetrisView();

        rightKey = loadKeySetting("right");
        leftKey = loadKeySetting("left");
        rotateKey = loadKeySetting("rotate");
        downKey = loadKeySetting("down");
        dropKey = loadKeySetting("drop");
        rightKeyCode = getKeyCodeFromString(rightKey);
        leftKeyCode = getKeyCodeFromString(leftKey);
        rotateKeyCode = getKeyCodeFromString(rotateKey);
        downKeyCode = getKeyCodeFromString(downKey);
        dropKeyCode = getKeyCodeFromString(dropKey);
    }

    public void addListener(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() { // 키 이벤트
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.P) {

                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.exit(0); // ESC 누르면 창 닫기
                }
            }
        });

        handleKeyInput(scene);
    }

    public void handleKeyInput(Scene scene) {

    }


    public void runGame(Stage stage) {
        view.setScene(stage);

        Thread thread1 = new Thread(() -> {});
        Thread finalThread = thread1;
        Runnable task = new Runnable() {
            public void run() {
                while (!controller.isGameOver) {
                    if (controller.isPaused)
                        continue;

                    MultiBoardController.generateTetromino(0);
                    MultiBoardController.generateTetromino(0);
                    view.color_mesh(0);

                    try {
                        finalThread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                    softDrop((TetrominoBase) model.bags[0].get(0), 0);


                }
            }
        };
        thread1 = new Thread(task);
        thread1.start();

        Thread thread2 = new Thread(() -> {});
        Thread finalThread2 = thread1;
        Runnable task2 = new Runnable() {
            public void run() {
                while (!controller.isGameOver) {
                    if (controller.isPaused)
                        continue;

                    MultiBoardController.generateTetromino(1);
                    MultiBoardController.generateTetromino(1);
                    view.color_mesh(1);

                    try {
                        finalThread2.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    softDrop((TetrominoBase) model.bags[1].get(0), 1);


                }
            }
        };
        thread2 = new Thread(task2);
        thread2.start();
    }

    public KeyCode getKeyCodeFromString(String keyName) {    //json -> KeyCode로 변경
        for (KeyCode kc : KeyCode.values()) {
            if (kc.getName().equalsIgnoreCase(keyName)) {
                return kc;
            }
        }
        return null;
    }

    private String loadKeySetting(String key) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/com/snust/tetrij/keysetting.json")), "UTF-8");
            JSONObject settings = new JSONObject(content);
            return settings.getString(key);
        } catch (Exception e) {

            return loadKeySetting_build(key);
        }
    }

    private String loadKeySetting_build(String key) {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/snust/tetrij/keysetting.json");
            if (inputStream == null) {
                System.err.println("설정 파일을 찾을 수 없습니다.");
                return null;
            }

            // 입력 스트림을 문자열로 변환
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            // JSON 객체 생성
            JSONObject settings = new JSONObject(stringBuilder.toString());
            return settings.getString(key);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}