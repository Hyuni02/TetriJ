package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import static com.snust.tetrij.Controller.GameOverController.scoreId;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;

public class ScoreBoardController {
    GameManager instance = GameManager.getInstance();
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
        scoreId = "";
        instance.switchToScene("start_menu.fxml");

    }

    @FXML
    public void switchToGameOver(ActionEvent event) throws IOException {
        Parent root = instance.loadFXML("game_over.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        // 콤보박스에 난이도 옵션 추가
        difficultyComboBox.getItems().addAll("EASY", "NORMAL", "HARD", "ITEM");


        // 콤보박스 선택 이벤트 핸들러 추가
        difficultyComboBox.setOnAction(event -> {
            String selectedDifficulty = difficultyComboBox.getValue();
            loadScores(selectedDifficulty);

        });

        // 초기 스코어 로드
        loadScores(controller_s.currentDifficulty.toString());
    }
    private void loadScores(String difficulty) {
        String filePath = "src/main/resources/com/snust/tetrij/score.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String[]> scores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (parts.length == 5) {
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
            String currentScoreId = scoreId != null ? scoreId : ""; // NullPointerException 대비
            // 상위 10개 레이블 업데이트
            List<Label> scoreLabels = List.of(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);
            for (int i = 0; i < Math.min(10, filteredScores.size()); i++) {
                String[] scoreData = filteredScores.get(i);

                // 콤보 박스 바꿨을 때도 글씨 크기 유지하기 위해서...
                String style = "-fx-font-size: 12pt;";
                if(instance.getPrimaryStage().getWidth() == 900 && instance.getPrimaryStage().getHeight() == 600){
                    style = "-fx-font-size: 20pt;";
                }
                else if(instance.getPrimaryStage().getWidth() == 1200 && instance.getPrimaryStage().getHeight() == 800) {
                    style = "-fx-font-size: 22pt;"; // 기본 스타일 설정
                }
                //

                if (currentScoreId.equals(scoreData[4])) {
                    System.out.println("성공!");
                    style += "-fx-text-fill: #ff8989; -fx-font-weight: bold;"; // 현재 점수 스타일 변경
                }
                scoreLabels.get(i).setStyle(style);
                scoreLabels.get(i).setText(formatScore(scoreData));
            }

            // 남은 레이블은 빈 텍스트로 초기화
            for (int i = filteredScores.size(); i < scoreLabels.size(); i++) {
                scoreLabels.get(i).setText("");
                scoreLabels.get(i).setStyle("");
            }
        } catch (IOException e) {
            loadScores_build(difficulty);
        }
    }


    private void loadScores_build(String difficulty) {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/com/snust/tetrij/score.txt");
            if (inputStream == null) {
                System.err.println("점수 파일을 찾을 수 없습니다.");
                return;
            }

            // 입력 스트림을 문자열로 변환
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String[]> scores = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");

                    if (parts.length == 5) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String formatScore(String[] scoreData) {
        String name = scoreData[0];
        String score = scoreData[1];
        String date = scoreData[2];
        String difficulty = scoreData[3];
        return name + ": " + score + " p      (" + date + ") (" + difficulty + ")";
    }
}