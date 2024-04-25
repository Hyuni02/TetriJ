package com.snust.tetrij;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectModeController extends GameManager {
    private Stage thisStage;

    public static void selectMode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SelectModeController.class.getResource("select_mode.fxml"));
        Parent root = fxmlLoader.load();

        Stage modeSelectStage = new Stage();
        modeSelectStage.initModality(Modality.APPLICATION_MODAL); // modality : 이걸로 띄우면 다른 창 이용 불가
        modeSelectStage.setScene(new Scene(root));
        modeSelectStage.setTitle("Select Mode!");

        // 컨트롤러에 Stage 전달
        SelectModeController controller = fxmlLoader.getController();
        controller.setStage(modeSelectStage); // Stage 설정

        // 모드 선택 창 표시
        modeSelectStage.showAndWait();
    }

    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

    @FXML
    private void selectEasy() throws Exception {
        startGame("EASY");
        closeStage();
    }

    @FXML
    private void selectNormal() throws Exception {
        startGame("NORMAL");
        closeStage();
    }

    @FXML
    private void selectHard() throws Exception {
        startGame("HARD");
        closeStage();
    }

    @FXML
    private void selectItem() throws Exception {
        startGame("ITEM");
        closeStage();
    }

    private void closeStage() {
        if (thisStage != null) {
            thisStage.close();
        }
    }
}