package com.snust.tetrij;

import com.snust.tetrij.Controller.ResolutionManager;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.exit;

public class GameManager {
    private static GameManager instance;
    private Stage stage;
    private Scene scene;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getPrimaryStage() {
        return stage;
    }

    public void setCurrentScene(Scene scene) {
        this.scene = scene;
        if (stage != null) {
            stage.setScene(scene);
        }
    }

    public Scene getCurrentScene() {
        return scene;
    }

    public void switchToScene(String fxml) throws IOException {
        Parent root = loadFXML(fxml); // root 불러오고

        Scene scene = new Scene(root); // 씬 생성
        setCurrentScene(scene); // 씬을 스테이지에 넣기

        // 해상도 설정
        if (fxml == "start_menu.fxml") {
            ResolutionManager.setStartMenuResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());

            // start menu에서 esc 누르면 종료되도록 설정
            scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        exit(0); // ESC 키를 누르면 창을 닫음
                    }
                }
            });
        } else if (fxml == "score_board.fxml") {
            ResolutionManager.setScoreBoardResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        } else if (fxml == "setting.fxml") {
            ResolutionManager.setSettingMenuResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        } else if (fxml == "keysetting.fxml") {
            ResolutionManager.setKeySettingMenuResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        } else {
            System.out.println("해상도 설정 오류 발생");
        }

        stage.show(); // 스테이지 보여주기
    }

    public void startGame(String diff) throws Exception {
        MainMenu.playTetrij(diff);
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        return loader.load();
    }

    public Path makeScoreTxt() {
        String homeDir = System.getProperty("user.home");
        Path filePath = Paths.get(homeDir, "tetrij_scores.txt");

        try {
            // 파일 존재 여부 확인
            if (!Files.exists(filePath)) {
                // 파일이 존재하지 않으면 새로운 파일 생성
                Files.createFile(filePath);
                System.out.println("생성 파일 : " + filePath);
            } else {
                System.out.println("파일 존재함");
            }
        } catch (IOException e) {
            System.err.println("Error handling the file: " + e.getMessage());
        }

        return filePath;
    }

    public static Path Jsonsetting(){
        String homeDir = System.getProperty("user.home");
        Path filePath = Paths.get(homeDir, "setting.json");

        try {
            // 파일 존재 여부 확인
            if (!Files.exists(filePath)) {
                System.out.println("test!!!");
                // 파일이 존재하지 않으면 새로운 파일 생성
                Files.createFile(filePath);
                System.out.println("생성 파일 : " + filePath);

                JSONObject currentSettings = new JSONObject();
                currentSettings.put("screenSize", "600x400");
                currentSettings.put("isColorBlind", false);

                // 설정을 파일에 저장
                try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                    writer.write(currentSettings.toString());
                    writer.flush();
                }
                System.out.println("Saved settings: " + currentSettings.toString());
            } else {
                System.out.println("setting 파일 존재함");
            }
        } catch (IOException e) {
            System.err.println("Error handling the file: " + e.getMessage());
        }

        return filePath;
    }
    public static Path JsonKeysetting() {
        String homeDir = System.getProperty("user.home");
        Path filePath = Paths.get(homeDir, "keysetting.json");

        try {
            // 파일 존재 여부 확인
            if (!Files.exists(filePath)) {
                System.out.println("test!!!");
                // 파일이 존재하지 않으면 새로운 파일 생성
                Files.createFile(filePath);
                System.out.println("생성 파일 : " + filePath);

                JSONObject currentSettings = new JSONObject();
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
            } else {
                System.out.println("keysetting 파일 존재함");
            }
        } catch (IOException e) {
            System.err.println("Error handling the file: " + e.getMessage());
        }

        return filePath;
    }
}
