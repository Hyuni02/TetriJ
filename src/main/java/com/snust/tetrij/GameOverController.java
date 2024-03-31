package com.snust.tetrij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class GameOverController extends GameManager {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField nameField;
    private int resultScore = 100;

    @FXML
    private void saveScore(ActionEvent event) throws IOException {
        String name = nameField.getText();
        String fileWriteText = name + " " + resultScore + " " + LocalDate.now() + '\n';

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/snust/tetrij/score.txt", true))) {
            writer.write(fileWriteText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = returnSceneRoot("score_board.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
