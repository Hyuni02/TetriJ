package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.GameManager;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel.model;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiBoardController.boardController;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisView.view;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;


public class MultiKeyController {
    public final static MultiKeyController keyController = new MultiKeyController();

    public String rightKey;
    public String leftKey;
    public String rotateKey;
    public String downKey;
    public String dropKey;

    public String rightKey1;
    public String leftKey1;
    public String rotateKey1;
    public String downKey1;
    public String dropKey1;

    public KeyCode rightKeyCode;
    public KeyCode leftKeyCode;
    public KeyCode rotateKeyCode;
    public KeyCode downKeyCode;
    public KeyCode dropKeyCode;

    public KeyCode rightKeyCode1;
    public KeyCode leftKeyCode1;
    public KeyCode rotateKeyCode1;
    public KeyCode downKeyCode1;
    public KeyCode dropKeyCode1;

    public void reloadKeySetting() {
        rightKey = loadKeySetting("right");
        leftKey = loadKeySetting("left");
        rotateKey = loadKeySetting("rotate");
        downKey = loadKeySetting("down");
        dropKey = loadKeySetting("drop");

        rightKey1 = loadKeySetting("p2Right");
        leftKey1 = loadKeySetting("p2Left");
        rotateKey1 = loadKeySetting("p2Rotate");
        downKey1 = loadKeySetting("p2Down");
        dropKey1 = loadKeySetting("p2Drop");

        rightKeyCode = getKeyCodeFromString(rightKey);
        leftKeyCode = getKeyCodeFromString(leftKey);
        rotateKeyCode = getKeyCodeFromString(rotateKey);
        downKeyCode = getKeyCodeFromString(downKey);
        dropKeyCode = getKeyCodeFromString(dropKey);

        rightKeyCode1 = getKeyCodeFromString(rightKey1);
        leftKeyCode1 = getKeyCodeFromString(leftKey1);
        rotateKeyCode1 = getKeyCodeFromString(rotateKey1);
        downKeyCode1 = getKeyCodeFromString(downKey1);
        dropKeyCode1 = getKeyCodeFromString(dropKey1);
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
            Path filePath = GameManager.JsonKeysetting();
            String content;
            content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            JSONObject settings = new JSONObject(content);
            return settings.getString(key);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void gameProc(Scene scene) {
        scene.setOnKeyPressed(e->{
            javafx.scene.input.KeyCode code = e.getCode();
            if (model.bags[0].isEmpty() || model.bags[1].isEmpty())
                code = KeyCode.NONCONVERT;

            if(code == KeyCode.NONCONVERT);
            else if(code == KeyCode.ESCAPE){
                System.out.println("esc");
                controller.isPaused = true;
                Platform.exit();
                System.exit(0);
            }
            else if (code == KeyCode.P){
                controller.isPaused = !controller_s.isPaused;
                controller.togglePause();
            }
            else if(code == leftKeyCode){
                boardController.moveLeftOnKeyPress((TetrominoBase)model.bags[0].get(0), 0);
                view.color_mesh(0);
            }
            else if(code == rightKeyCode){
                boardController.moveRightOnKeyPress((TetrominoBase)model.bags[0].get(0), 0);
                view.color_mesh(0);
            }
            else if(code == rotateKeyCode){
                boardController.rotateClockWise((TetrominoBase)model.bags[0].get(0), 0);
                view.color_mesh(0);
            }
            else if(code == downKeyCode){
                boardController.softDrop((TetrominoBase)model.bags[0].get(0), 0);
                view.color_mesh(0);
            }
            else if(code == dropKeyCode){
                boardController.hardDrop((TetrominoBase)model.bags[0].get(0), 0);
                view.color_mesh(0);
            }
            //p2 controller
            else if(code == leftKeyCode1){
                boardController.moveLeftOnKeyPress((TetrominoBase)model.bags[1].get(0), 1);
                view.color_mesh(1);
            }
            else if(code == rightKeyCode1){
                boardController.moveRightOnKeyPress((TetrominoBase)model.bags[1].get(0), 1);
                view.color_mesh(1);
            }
            else if(code == rotateKeyCode1){
                boardController.rotateClockWise((TetrominoBase)model.bags[1].get(0), 1);
                view.color_mesh(1);
            }
            else if(code == downKeyCode1){
                boardController.softDrop((TetrominoBase)model.bags[1].get(0), 1);
                view.color_mesh(1);
            }
            else if(code == dropKeyCode1){
                boardController.hardDrop((TetrominoBase)model.bags[1].get(0), 1);
                view.color_mesh(1);
            }
            else if (code == KeyCode.P) {
                controller.togglePause();
            }
        });
    }

}
