package com.snust.tetrij;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.json.JSONObject;

import javafx.scene.control.Button;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class KeySettingController {
    @FXML
    private Button leftMoveKeyButton;

    public void initialize() {
        loadKeySettings();
    }

    private void loadKeySettings() {    //키셋팅 파일 읽어옴
        try {
            File file = new File("keysetting.json");
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            fileReader.close();

            JSONObject keySettings = new JSONObject(stringBuilder.toString());
            leftMoveKeyButton.setText(keySettings.getString("left"));
        } catch (Exception e) {
            e.printStackTrace();
            leftMoveKeyButton.setText("블록 왼쪽 이동 버튼 설정");
        }
    }

    public void handleLeftKeyInput() {  //왼쪽버튼 입력값 받을 준비
        leftMoveKeyButton.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }
    private void handleKeyPressed(KeyEvent keyEvent) {  //입력값 받고 저장하기
        String keyName = keyEvent.getCode().toString();
        JSONObject keySettings = new JSONObject();
        keySettings.put("left", keyName);
        saveKeySettingsToFile(keySettings);

        keyEvent.consume();
        leftMoveKeyButton.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }


    private void saveKeySettingsToFile(JSONObject keySettings) {   //json 파일로 저장
        try {
            File file = new File("keysetting.json");
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(keySettings.toString());
                fileWriter.flush();
                loadKeySettings();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
