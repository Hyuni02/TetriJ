package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import com.snust.tetrij.Tetris;
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

public class GameOverController {
    private final static GameManager instance = GameManager.getInstance();
    @FXML
    private Text scoreText;
    @FXML
    private TextField nameField;
    private static int resultScore;
    private static String diff;
    public static String scoreId = "";

    @FXML
    private void initialize() {
        String displayStr = "Score : " + Integer.toString(resultScore);
        scoreText.setText(displayStr);
    }

    @FXML
    private void saveScore(ActionEvent event) throws IOException {
        String name = nameField.getText().replace(" ", "");
        scoreId = UUID.randomUUID().toString(); // 고유 식별자 생성

        String fileWriteText = name + " " + resultScore + " " + LocalDate.now() + " " + diff + " " + scoreId + '\n';

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/snust/tetrij/score.txt", true))) {
            writer.write(fileWriteText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // 종료하면 setOnHidden 이벤트 핸들러 작동
    }

    public static void switchToGameOver(int score, Tetris.difficulty difficulty) {

        resultScore = score;
        diff = difficulty.name();

        FXMLLoader loader = new FXMLLoader(GameOverController.class.getResource("game_over.fxml"));
        Parent root = null;
        try {

            root = loader.load();  // 루트 load

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // 게임 오버 창이 닫히면 테트리스 스코어보드로 넘어가도록 설정
            stage.setOnHidden(event -> {
                try {

                    instance.switchToScene("score_board.fxml"); // 메인 스테이지를 스코어보드로 전환

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

    }
}