package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardController extends GameManager {
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
        stage.show();
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
        loadScores();
    }

    private void loadScores() {
        String filePath = "src/main/resources/com/snust/tetrij/score.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String[]> scores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (parts.length == 3) { // 배열의 길이가 (이름, 점수, 날짜) 3개여야 함
                    scores.add(parts);
                }
            }
            scores.sort((s1, s2) -> Integer.compare(Integer.parseInt(s2[1]), Integer.parseInt(s1[1]))); // 내림차순 정렬
            List<Label> scoreLabels = List.of(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);
            for (int i = 0; i < Math.min(10, scores.size()); i++) { // 상위 10개 스코어만 사용
                String[] scoreData = scores.get(i);
                Label label = scoreLabels.get(i);
                label.setText(scoreData[0] + ": " + scoreData[1] + " (Date: " + scoreData[2] + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}