package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;

public class SelectModeController {

    public static SelectModeController instance;
    private Stage thisStage;
    private Scene thisScene;

    public static SelectModeController getInstance(){
        if(instance == null){
            instance = new SelectModeController();
        }

        return instance;
    }

    public void selectMode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game_mode.fxml"));
        Parent root = fxmlLoader.load();

        thisStage = new Stage();
        thisStage.initModality(Modality.APPLICATION_MODAL); // modality : 이걸로 띄우면 다른 창 이용 불가
        thisScene = new Scene(root);
        thisStage.setScene(thisScene);
        thisStage.setTitle("Select Mode!");

        // 컨트롤러에 Stage 전달
        SelectModeController controller = fxmlLoader.getController();
        controller.setStage(thisStage); // Stage 설정

        // 모드 선택 창 표시
        thisStage.show();
    }

    public void selectSingle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("single_mode.fxml"));
        Parent root = fxmlLoader.load();

        thisScene = new Scene(root); // 씬 생성
        thisStage.setScene(thisScene);
        thisStage.setTitle("Select Mode!");

        // 컨트롤러에 Stage 전달
        SelectModeController controller = fxmlLoader.getController();
        controller.setStage(thisStage); // Stage 설정

        thisStage.show();
    }

    public void selectBattle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("battle_mode.fxml"));
        Parent root = fxmlLoader.load();

        thisScene = new Scene(root); // 씬 생성
        thisStage.setScene(thisScene);
        thisStage.setTitle("Select Mode!");

        // 컨트롤러에 Stage 전달
        SelectModeController controller = fxmlLoader.getController();
        controller.setStage(thisStage); // Stage 설정

        thisStage.show();
    }

    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

    @FXML
    private void selectEasy() throws Exception {
        GameManager.getInstance().startGame("EASY");
        closeStage();
    }

    @FXML
    private void selectNormal() throws Exception {
        GameManager.getInstance().startGame("NORMAL");
        closeStage();
    }

    @FXML
    private void selectHard() throws Exception {
        GameManager.getInstance().startGame("HARD");
        closeStage();
    }

    @FXML
    private void selectItem() throws Exception {
        GameManager.getInstance().startGame("ITEM");
        closeStage();
    }

    @FXML
    private void selectBattleEasy() {
        controller.runGame(MultiTetrisController.difficulty.EASY);
    }

    @FXML
    private void selectBattleNormal() {
        controller.runGame(MultiTetrisController.difficulty.NORMAL);
    }

    @FXML
    private void selectBattleHard() {
        controller.runGame(MultiTetrisController.difficulty.HARD);
    }

    @FXML
    private void selectBattleItem() {
        controller.runGame(MultiTetrisController.difficulty.ITEM);
    }

    private void closeStage() {
        if (thisStage != null) {
            thisStage.close();
        }
    }
}
