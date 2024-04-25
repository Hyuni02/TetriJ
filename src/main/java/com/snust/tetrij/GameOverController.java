package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class GameOverController extends GameManager {
    private static Stage stage;
    private static Scene scene;
    private static Stage tetrisStage;
    @FXML
    private Text scoreText;
    @FXML
    private TextField nameField;
    private static int resultScore;
    private static String diff;
    private static String scoreId;
    @FXML
    private void initialize() {
        String displayStr = "Score : " + Integer.toString(resultScore);
        scoreText.setText(displayStr);
    }
    @FXML
    private void saveScore(ActionEvent event) throws IOException {
        String name = nameField.getText().replace(" ","");
        scoreId = UUID.randomUUID().toString(); // 고유 식별자 생성

        String fileWriteText = name + " " + resultScore + " " + LocalDate.now() + " " + diff + " " + scoreId + '\n';

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/snust/tetrij/score.txt", true))) {
            writer.write(fileWriteText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close(); // 종료하면 setOnHidden 이벤트 핸들러 작동
    }

    public static void switchToGameOver(int score, Stage tetrisStage, Tetris.difficulty difficulty) {
        try {
            resultScore = score;
            diff = difficulty.name();

            FXMLLoader loader = new FXMLLoader(GameOverController.class.getResource("game_over.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // 게임 오버 창이 닫히면 테트리스 스코어보드로 넘어가도록 설정
            stage.setOnHidden(event -> {
                FXMLLoader scoreLoader = new FXMLLoader(GameOverController.class.getResource("score_board.fxml"));
                Parent scoreRoot;
                try {
                    scoreRoot = scoreLoader.load();

                    // 스코어보드 컨트롤러에서 가장 최근 점수의 고유 식별자를 강조하도록 설정
                    ScoreBoardController scoreBoardController = scoreLoader.getController();
                    scoreBoardController.highlightRecentScore(scoreId); // 최근 점수 강조

                    tetrisStage.setScene(new Scene(scoreRoot));
                    SetResolution.setScoreBoardResolution(scoreRoot, (int) tetrisStage.getHeight(), (int) tetrisStage.getWidth());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tetrisStage.show();
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}