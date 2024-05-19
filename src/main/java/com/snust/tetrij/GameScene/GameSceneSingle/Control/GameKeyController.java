package com.snust.tetrij.GameScene.GameSceneSingle.Control;

import com.snust.tetrij.Tetris;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;
import static com.snust.tetrij.Tetris.*;

public class GameKeyController {
    public static String rightKey = loadKeySetting("right");
    public static String leftKey = loadKeySetting("left");
    public static String rotateKey = loadKeySetting("rotate");
    public static String downKey = loadKeySetting("down");
    public static String dropKey = loadKeySetting("drop");
    public static KeyCode rightKeyCode = getKeyCodeFromString(rightKey);
    public static KeyCode leftKeyCode = getKeyCodeFromString(leftKey);
    public static KeyCode rotateKeyCode = getKeyCodeFromString(rotateKey);
    public static KeyCode downKeyCode = getKeyCodeFromString(downKey);
    public static KeyCode dropKeyCode = getKeyCodeFromString(dropKey);

    public static void addListenerPause(Scene scene) {

    }

    /**
     * key event 처리
     * @param scene
     */
    public static void addListenerGameControl(Scene scene) {
        scene.setOnKeyPressed(e->{
            javafx.scene.input.KeyCode code = e.getCode();
            if (TetrisBoardController.bag.isEmpty())
                code = KeyCode.NONCONVERT;

            if(code == KeyCode.NONCONVERT);
            else if(code == KeyCode.ESCAPE){
                System.out.println("esc");
                controller_s.isPaused = !controller_s.isPaused;
            }
            else if(code == leftKeyCode){
                TetrisBoardController.moveLeftOnKeyPress(TetrisBoardController.bag.get(0));
                view_s.color_mesh();
            }
            else if(code == rightKeyCode){
                TetrisBoardController.moveRightOnKeyPress(TetrisBoardController.bag.get(0));
                view_s.color_mesh();
            }
            else if(code == rotateKeyCode){
                TetrisBoardController.rotateClockWise(TetrisBoardController.bag.get(0));
                view_s.color_mesh();
            }
            else if(code == downKeyCode){
                TetrisBoardController.softDrop(TetrisBoardController.bag.get(0));
                view_s.color_mesh();
            }
            else if(code == dropKeyCode){
                TetrisBoardController.hardDrop(TetrisBoardController.bag.get(0));
                view_s.color_mesh();
            }
        });

    }

    public static KeyCode getKeyCodeFromString(String keyName) {    //json -> KeyCode로 변경
        for (KeyCode kc : KeyCode.values()) {
            if (kc.getName().equalsIgnoreCase(keyName)) {
                return kc;
            }
        }
        return null;
    }

    private static String loadKeySetting(String key) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/com/snust/tetrij/keysetting.json")), "UTF-8");
            JSONObject settings = new JSONObject(content);
            return settings.getString(key);
        } catch (Exception e) {
//            e.printStackTrace();
            return loadKeySetting_build(key);
        }
    }

    private static String loadKeySetting_build(String key) {
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
