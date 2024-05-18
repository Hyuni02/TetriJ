package com.snust.tetrij.Controller;
import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.snust.tetrij.Controller.ResolutionManager.*;

public class SettingController {
    private Stage stage;
    private Scene scene;
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
    public void defaultSetting(){
        File file = new File("src/main/resources/com/snust/tetrij/setting.json");
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
            JSONObject currentSettings = new JSONObject(content);
            currentSettings.put("screenSize", "600x400");
            currentSettings.put("isColorBlind", false);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(currentSettings.toString());
                fileWriter.flush();
                loadSettings();
                colorBlindModeCheckBox.setSelected(isColorBlind);
                sizeComboBox.getSelectionModel().select(screenSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        file = new File("src/main/resources/com/snust/tetrij/keysetting.json");
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
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

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(currentSettings.toString());
                fileWriter.flush();
            }
        } catch (Exception e) {
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

    private void saveSettingsToFile() {   //json 파일로 저장
        File file = new File("src/main/resources/com/snust/tetrij/setting.json");
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
            JSONObject currentSettings = new JSONObject(content);
            currentSettings.put("screenSize", screenSize);
            currentSettings.put("isColorBlind", isColorBlind);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(currentSettings.toString());
                fileWriter.flush();
                loadSettings();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            saveSettingsToFile_build();
        }
    }

    private void saveSettingsToFile_build() {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            InputStream inputStream = this.getClass().getResourceAsStream("com/snust/tetrij/setting.json");
            if (inputStream == null) {
                System.err.println("설정 파일을 찾을 수 없습니다.");
                return;
            }
            byte[] bytes = inputStream.readAllBytes();

            // 파일 읽기
            String content = new String(bytes, StandardCharsets.UTF_8);
            JSONObject currentSettings = new JSONObject(content);

            // 설정 값 업데이트
            currentSettings.put("screenSize", screenSize);
            currentSettings.put("isColorBlind", isColorBlind);

            // 파일 쓰기
            File file = new File(getClass().getResource("/com/snust/tetrij/setting.json").getFile());
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(currentSettings.toString());
                fileWriter.flush();
            }
            loadSettings();
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
    public void deleteScores() { // 스코어보드 초기화 메서드
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("스코어보드 초기화");
        alert.setHeaderText("정말 스코어보드를 초기화하시겠습니까?");
        alert.setContentText("초기화하려면 확인을 누르세요.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                BufferedWriter writer = null;
                try {
                    writer = Files.newBufferedWriter(Paths.get("src/main/resources/com/snust/tetrij/score.txt"));
                    writer.write("");
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
    public void handleKeySetting(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("keysetting.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {    //셋팅 파일 읽어옴
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
            screenSize = setting.getString("screenSize");
            isColorBlind = setting.getBoolean("isColorBlind");

        } catch (Exception e) {
//            e.printStackTrace();
            loadSettings_build();
        }
    }

    private static void loadSettings_build() {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/snust/tetrij/setting.json");
            if (inputStream == null) {
                System.err.println("설정 파일을 찾을 수 없습니다.");
                return;
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
            JSONObject setting = new JSONObject(stringBuilder.toString());
            curResolution = setting.getString("screenSize");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No setting.json");
        }
    }
}