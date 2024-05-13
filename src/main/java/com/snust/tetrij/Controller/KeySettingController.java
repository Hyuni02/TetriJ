package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import org.json.JSONObject;

import javafx.scene.control.Button;
import org.json.JSONTokener;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class KeySettingController{
    private final GameManager instance = GameManager.getInstance();
    @FXML
    private Button leftMoveKeyButton;
    @FXML
    private Button rightMoveKeyButton;
    @FXML
    private Button rotateMoveKeyButton;
    @FXML
    private Button downMoveKeyButton;
    @FXML
    private Button dropKeyButton;
    @FXML
    private Button p2LeftButton;
    @FXML
    private Button p2RightButton;
    @FXML
    private Button p2RotateButton;
    @FXML
    private Button p2DownButton;
    @FXML
    private Button p2DropButton;
    private EventHandler<KeyEvent> currentKeyEventHandler;  //현재 키 이벤트가 활성화되어있는가?
    private List<Button> allButtons = new ArrayList<>();    //버튼 입력 이벤트 초기화시 쓰임

    public void initialize() {
        allButtons.add(leftMoveKeyButton);
        allButtons.add(rightMoveKeyButton);
        allButtons.add(rotateMoveKeyButton);
        allButtons.add(downMoveKeyButton);
        allButtons.add(dropKeyButton);
        allButtons.add(p2LeftButton);
        allButtons.add(p2RightButton);
        allButtons.add(p2RotateButton);
        allButtons.add(p2DownButton);
        allButtons.add(p2DropButton);
        removeCurrentKeyEventHandler(); //예전이벤트 삭제
        loadKeySettings();  //json파일 읽어옴
    }

    @FXML
    private void handleLeftKeyInput() {
        setupKeyButton(leftMoveKeyButton, "left");
    }
    @FXML
    private void handleRightKeyInput() {
        setupKeyButton(rightMoveKeyButton, "right");
    }
    @FXML
    private void handleRotateKeyInput() {
        setupKeyButton(rotateMoveKeyButton, "rotate");
    }
    @FXML
    private void handleDownKeyInput() {
        setupKeyButton(downMoveKeyButton, "down");
    }
    @FXML
    private void handleDropInput() {
        setupKeyButton(dropKeyButton, "drop");
    }

    //플레이어 2 관련
    @FXML
    private void handleP2LeftKeyInput() {
        setupKeyButton(p2LeftButton, "p2Left");
    }
    @FXML
    private void handleP2RightKeyInput() {
        setupKeyButton(p2RightButton, "p2Right");
    }
    @FXML
    private void handleP2RotateKeyInput() {
        setupKeyButton(p2RotateButton, "p2Rotate");
    }
    @FXML
    private void handleP2DownKeyInput() {
        setupKeyButton(p2DownButton, "p2Down");
    }
    @FXML
    private void handleP2DropInput() {
        setupKeyButton(p2DropButton, "p2Drop");
    }

    private void loadKeySettings() {    //키셋팅 파일 읽어옴
        try {
            File file = new File("src/main/resources/com/snust/tetrij/keysetting.json");            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            fileReader.close();

            JSONObject keySettings = new JSONObject(stringBuilder.toString());
            leftMoveKeyButton.setText(keySettings.getString("left"));
            rightMoveKeyButton.setText(keySettings.getString("right"));
            rotateMoveKeyButton.setText(keySettings.getString("rotate"));
            downMoveKeyButton.setText(keySettings.getString("down"));
            dropKeyButton.setText(keySettings.getString("drop"));

            p2LeftButton.setText(keySettings.getString("p2Left"));
            p2RightButton.setText(keySettings.getString("p2Right"));
            p2RotateButton.setText(keySettings.getString("p2Rotate"));
            p2DownButton.setText(keySettings.getString("p2Down"));
            p2DropButton.setText(keySettings.getString("p2Drop"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setupKeyButton(Button button, String direction) {  //키 입력 받을 준비함
        removeCurrentKeyEventHandler(); // 이전에 설정된 키 이벤트 핸들러를 제거
        currentKeyEventHandler = keyEvent -> handleKeyPressed(keyEvent, direction, button); //이벤트 핸들러에 추가시킴(한번에 2개씩 바뀌는 버그 방지용)
        button.getScene().addEventFilter(KeyEvent.KEY_PRESSED, currentKeyEventHandler); //키 입력 받을 준비를 함
    }

    private void handleKeyPressed(KeyEvent keyEvent, String direction, Button button) { //키 누른거 json형으로 변환
        String keyName = keyEvent.getCode().toString();
        JSONObject keySettings = new JSONObject();
        if(keyName == "CONTROL"){
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("등록 불가능한 키");
                alert.setHeaderText(null);
                alert.setContentText("등록 불가능한 키입니다. 다른 값을 입력해주세요.");
                alert.showAndWait();
            });
            return; // 메소드 종료
        }
        

        //키 중복여부 ㄱㄱ
        if (isKeyAlreadyRegistered(direction, keyName)) {
            // 중복된 키 값에 대한 알림을 Alert 팝업으로 띄움
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("중복된 키");
                alert.setHeaderText(null);
                alert.setContentText("중복된 키 값입니다. 다른 값을 입력해주세요.");
                alert.showAndWait();
            });
            return; // 메소드 종료
        }


        keySettings.put(direction, keyName);
        saveKeySettingsToFile(keySettings); //keysetting.json에 저장하도록 함

        keyEvent.consume();
        removeCurrentKeyEventHandler(); //이벤트 잘 종료되었다고 ㄱㄱ / 근데 초반에 검사를 하니 렉걸리면 빼기
    }

    private JSONObject readKeySettingsFromFile() {  // json 저장되어있는것 가져오는 용도
        JSONObject keySettings = new JSONObject();
        try (FileReader reader = new FileReader("src/main/resources/com/snust/tetrij/keysetting.json")) {
            keySettings = new JSONObject(new JSONTokener(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keySettings;
    }
    private boolean isKeyAlreadyRegistered(String direction, String keyName) {  //중복키 검사
        JSONObject keySettings = readKeySettingsFromFile();

        for (String key : keySettings.keySet()) {
            if (keySettings.getString(key).equals(keyName) && !key.equals(direction)) { //중복된 키라면
                return true; //중복!
            }
        }
        return false; // 중복 ㄴㄴ
    }


    private void removeCurrentKeyEventHandler() {   //이전값 안누른채로 종료되면 다음값이랑 같이 바뀌는 버그 수정용
        for (Button button : allButtons) {
            Scene scene = button.getScene();
            if (scene != null && currentKeyEventHandler != null) {
                scene.removeEventFilter(KeyEvent.KEY_PRESSED, currentKeyEventHandler);
            }
        }
        currentKeyEventHandler = null;
    }


    private void saveKeySettingsToFile(JSONObject keySettings) {   //json 파일로 저장
        File file = new File("src/main/resources/com/snust/tetrij/keysetting.json");

        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
            JSONObject currentSettings = new JSONObject(content);

            for(String key : currentSettings.keySet()) {
                if(keySettings.has(key)) {
                    // 새로 바꾼 keySetiing의 key값과 json에 있는 key값이 일치하다면
                    // keySetting의 값을 저장
                    currentSettings.put(key, keySettings.get(key));
                }
                else
                    currentSettings.put(key, currentSettings.get(key));
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(currentSettings.toString());
                fileWriter.flush();
                loadKeySettings();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToSetting(ActionEvent event) throws IOException {
        instance.switchToScene("setting.fxml");
    }

}
