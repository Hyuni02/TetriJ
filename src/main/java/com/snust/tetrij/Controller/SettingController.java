package com.snust.tetrij.Controller;
import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;

import javafx.stage.Stage;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import static com.snust.tetrij.Controller.ResolutionManager.*;

public class SettingController {
    Stage stage;
    Scene scene;
    GameManager instance = GameManager.getInstance();
    @FXML
    private ComboBox<String> sizeComboBox;
    @FXML
    private CheckBox colorBlindModeCheckBox;
    private String screenSize;
    private boolean isColorBlind;

    public void initialize() {
        loadSettings();  //json파일 읽어옴
        colorBlindModeCheckBox.setSelected(isColorBlind);
        sizeComboBox.getSelectionModel().select(screenSize);
    }

    // 해상도 변경 메서드
    // -> no usage 떠서 주석처리 할게용
//    public static void setResolution(int width, int height) {
//        resolutionX = width;
//        resolutionY = height;
//    }
    @FXML
    private void handleSize() {
        screenSize = sizeComboBox.getSelectionModel().getSelectedItem();
    }
    public void defaultSetting() {
        System.out.println("test1");

        try {
            System.out.println("test2");

            String homeDir = System.getProperty("user.home");
            Path filePath = Paths.get(homeDir, "setting.json");

            System.out.println("셋팅 디폴트 초기화!");

            String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            JSONObject currentSettings = new JSONObject(content);

            currentSettings.put("screenSize", "600x400");
            currentSettings.put("isColorBlind", false);

            //설정을 파일에 저장
            colorBlindModeCheckBox.setSelected(isColorBlind);
            sizeComboBox.getSelectionModel().select(screenSize);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                writer.write(currentSettings.toString());
                writer.flush();
            }

            loadSettings(); // 설정 적용
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            String homeDir = System.getProperty("user.home");
            Path filePath = Paths.get(homeDir, "keysetting.json");
            System.out.println("키셋팅 디폴트 초기화! ");

            String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            JSONObject currentSettings = new JSONObject(content);
            currentSettings.put("right", "RIGHT");
            currentSettings.put("left", "LEFT");
            currentSettings.put("rotate", "UP");
            currentSettings.put("down", "DOWN");
            currentSettings.put("drop", "SPACE");
            //플레이어2
            currentSettings.put("p2Right", "D");
            currentSettings.put("p2Left", "A");
            currentSettings.put("p2Rotate", "W");
            currentSettings.put("p2Down", "S");
            currentSettings.put("p2Drop", "SHIFT");

            // 설정을 파일에 저장
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                writer.write(currentSettings.toString());
                writer.flush();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void colorBlindMode(){
        isColorBlind = colorBlindModeCheckBox.isSelected();
    }

    @FXML
    public void saveSetting(){
        saveSettingsToFile();
    }

    private void saveSettingsToFile() {
        // 빌드 환경에서 셋팅파일을 저장
        try {
            // 홈 디렉터리에서 설정 파일 경로 얻기
            Path filePath = GameManager.Jsonsetting();
            String content;
            JSONObject currentSettings = new JSONObject();

            // 파일로부터 설정 읽기
            content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            currentSettings = new JSONObject(content);

            // 설정 쓰기
            currentSettings.put("screenSize", screenSize);
            currentSettings.put("isColorBlind", isColorBlind);

            // 파일에 설정 저장
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                writer.write(currentSettings.toString());
                writer.flush();
            }

            loadSettings(); // 설정 적용
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //시작 메뉴로 가기
    public void switchToStartMenu(ActionEvent event) throws IOException {
        resolutionInitialize();
        saveSettingsToFile();
        Parent root = instance.loadFXML("start_menu.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(curHeight);
        stage.setWidth(curWidth);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close(); // ESC 키를 누르면 창을 닫음
                }
            }
        });
        stage.show();
        System.out.println(stage.getHeight());
        System.out.println(stage.getWidth());
        ResolutionManager.setStartMenuResolution(root, (int) stage.getHeight(), (int) stage.getWidth());
    }
//    public void deleteScores() { // 스코어보드 초기화 메서드
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("스코어보드 초기화");
//        alert.setHeaderText("정말 스코어보드를 초기화하시겠습니까?");
//        alert.setContentText("초기화하려면 확인을 누르세요.");
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                BufferedWriter writer = null;
//                try {
//                    writer = Files.newBufferedWriter(Paths.get("src/main/resources/com/snust/tetrij/score.txt"));
//                    writer.write("");
//                    writer.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });
//
//    }
public void deleteScores() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("스코어보드 초기화");
    alert.setHeaderText("정말 스코어보드를 초기화하시겠습니까?");
    alert.setContentText("초기화하려면 확인을 누르세요.");
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {

            try (BufferedWriter writer = Files.newBufferedWriter(instance.makeScoreTxt())) {
                writer.write(""); // 파일 내용을 비움
                writer.flush();
                System.out.println("스코어보드가 초기화되었습니다.");
            } catch (IOException e) {
                System.err.println("스코어 파일 초기화 중 오류 발생: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    });
}
    public void switchToKeySetting() throws IOException {
//            Parent root = FXMLLoader.load(getClass().getResource("keysetting.fxml"));
//            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.getScene().setRoot(root);
            instance.switchToScene("keysetting.fxml");
    }

    private void loadSettings() {
    // 개발 환경에서 셋팅 파일 읽어옴
        // 빌드 환경에서 셋팅 파일 읽어옴
        Path filePath = GameManager.Jsonsetting();

        // 파일 읽기
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // 설정 값 가져오기
        JSONObject setting = new JSONObject(stringBuilder.toString());
        screenSize = setting.getString("screenSize");
        isColorBlind = setting.getBoolean("isColorBlind");
        curResolution = setting.getString("screenSize");

        System.out.println("Current Resolution: " + curResolution);
    }

}