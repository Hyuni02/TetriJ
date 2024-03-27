package com.snust.tetrij;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tetris extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    protected void startGame() {
        // 게임 시작
        int score;
        System.out.println("게임 시작");
    }

    protected void openSetting() {
        // 환경설정 실행
        System.out.println("환경 설정");
    }

    protected void openScoreboard() {
        // 환경설정 실행
        System.out.println("스코어보드");
    }
    public static void main(String[] args) {
        launch();
    }
}