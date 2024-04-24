package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardController extends GameManager {
    @FXML
    private ComboBox<String> difficultyComboBox; // 콤보박스 멤버 변수 추가
    private Stage stage;
    private Scene scene;

    @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Label score3;

    @FXML
    private Label score4;

    @FXML
    private Label score5;

    @FXML
    private Label score6;

    @FXML
    private Label score7;

    @FXML
    private Label score8;

    @FXML
    private Label score9;

    @FXML
    private Label score10;

    @FXML
    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("start_menu.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close(); // ESC 키를 누르면 창을 닫음
                }
            }
        });
        stage.show();
        com.snust.tetrij.SetResolution.setResolution(root, (int) stage.getHeight(), (int) stage.getWidth());
    }

    @FXML
    public void switchToGameOver(ActionEvent event) throws IOException {
        Parent root = returnSceneRoot("game_over.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        // 콤보박스에 난이도 옵션 추가
        difficultyComboBox.getItems().addAll("EASY", "NORMAL", "HARD");

        // 콤보박스 선택 이벤트 핸들러 추가
        difficultyComboBox.setOnAction(event -> {
            String selectedDifficulty = difficultyComboBox.getValue();
            loadScores(selectedDifficulty);
        });

        // 초기 스코어 로드
        loadScores("EASY");
    }
    private void loadScores(String difficulty) {
        String filePath = "src/main/resources/com/snust/tetrij/score.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String[]> scores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (parts.length == 4) {
                    scores.add(parts);
                }
            }

            // 선택된 난이도에 따른 스코어 필터링 및 정렬
            List<String[]> filteredScores = new ArrayList<>();
            for (String[] scoreData : scores) {
                if (scoreData[3].equals(difficulty)) {
                    filteredScores.add(scoreData);
                }
            }

            filteredScores.sort((s1, s2) -> Integer.compare(Integer.parseInt(s2[1]), Integer.parseInt(s1[1])));

            // 상위 10개 레이블 업데이트
            List<Label> scoreLabels = List.of(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);
            for (int i = 0; i < Math.min(10, filteredScores.size()); i++) {
                String[] scoreData = filteredScores.get(i);
                scoreLabels.get(i).setText(formatScore(scoreData));
            }

            // 남은 레이블은 빈 텍스트로 초기화
            for (int i = filteredScores.size(); i < scoreLabels.size(); i++) {
                scoreLabels.get(i).setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatScore(String[] scoreData) {
        String name = scoreData[0];
        String score = scoreData[1];
        String date = scoreData[2];
        String difficulty = scoreData[3];
        return name + ": " + score + "점 (날짜: " + date + ", 난이도: " + difficulty + ")";
    }


}