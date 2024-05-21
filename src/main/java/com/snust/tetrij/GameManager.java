package com.snust.tetrij;

import com.snust.tetrij.Controller.ResolutionManager;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class GameManager {
    private static GameManager instance;
    private Stage stage;
    private Scene scene;

    private GameManager() {
    }
    public static GameManager getInstance(){
        if(instance == null){
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
        if(fxml == "start_menu.fxml") {
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
        }
        else if(fxml == "score_board.fxml"){
            ResolutionManager.setScoreBoardResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        }
        else if(fxml == "setting.fxml"){
            ResolutionManager.setSettingMenuResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        }
        else if(fxml == "keysetting.fxml"){
            ResolutionManager.setKeySettingMenuResolution(stage.getScene().getRoot(),
                    (int) stage.getHeight(),
                    (int) stage.getWidth());
        }
        else {
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
}
