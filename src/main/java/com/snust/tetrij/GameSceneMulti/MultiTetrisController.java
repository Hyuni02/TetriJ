package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;
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

import static com.snust.tetrij.GameSceneMulti.MultiTetrisView.view;

public class MultiTetrisController {
    public final static MultiTetrisController controller = new MultiTetrisController();

    public String rightKey;
    public String leftKey;
    public String rotateKey;
    public String downKey;
    public String dropKey;
    public String p2RightKey;
    public String p2LeftKey;
    public String p2RotateKey;
    public String p2DownKey;
    public String p2DropKey;
    public KeyCode rightKeyCode;
    public KeyCode leftKeyCode;
    public KeyCode rotateKeyCode;
    public KeyCode downKeyCode;
    public KeyCode dropKeyCode;
    public KeyCode p2RightKeyCode;
    public KeyCode p2LeftKeyCode;
    public KeyCode p2RotateKeyCode;
    public KeyCode p2DownKeyCode;
    public KeyCode p2DropKeyCode;

    public boolean isPaused = false;
    public boolean isGameOver = false;

    public MultiTetrisController() {
        rightKey = loadKeySetting("right");
        leftKey = loadKeySetting("left");
        rotateKey = loadKeySetting("rotate");
        downKey = loadKeySetting("down");
        dropKey = loadKeySetting("drop");
        p2RightKey = loadKeySetting("p2Right");
        p2LeftKey = loadKeySetting("p2Left");
        p2RotateKey = loadKeySetting("p2Rotate");
        p2DownKey = loadKeySetting("p2Down");
        p2DropKey = loadKeySetting("p2Drop");

        rightKeyCode = getKeyCodeFromString(rightKey);
        leftKeyCode = getKeyCodeFromString(leftKey);
        rotateKeyCode = getKeyCodeFromString(rotateKey);
        downKeyCode = getKeyCodeFromString(downKey);
        dropKeyCode = getKeyCodeFromString(dropKey);
        p2RightKeyCode = getKeyCodeFromString(p2RightKey);
        p2LeftKeyCode = getKeyCodeFromString(p2LeftKey);
        p2RotateKeyCode = getKeyCodeFromString(p2RotateKey);
        p2DownKeyCode = getKeyCodeFromString(p2DownKey);
        p2DropKeyCode = getKeyCodeFromString(p2DropKey);
    }

    /**
     * 퍼즈 이벤트 핸들러 등록
     * @param scene : game Scene
     */
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

    /**
     * 테트로미노 조작 이벤트 핸들러 등록
     * @param scene : game Scene
     */
    public void handleKeyInput(Scene scene) {

    }


    public void runGame(Stage stage) {
        view.setScene(stage);

        PlayerThread p1 = new PlayerThread(0, "p1");
        PlayerThread p2 = new PlayerThread(1, "p2");
        p1.start();
        p2.start();
//      try {
//            p1.join();
//            p2.join();
//        } catch (InterruptedException e) {
//            System.out.println("Fatal Error: " + e.getMessage());
//        }

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