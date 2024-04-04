package com.snust.tetrij;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingController extends GameManager {
    private Stage stage;
    private Scene scene;
    @FXML
    private ComboBox<String> resolutionComboBox;

    // 해상도 변경 메서드
    public static void setResolution(int width, int height) {
        resolutionX = width;
        resolutionY = height;
    }

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("start_menu.fxml");
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
}