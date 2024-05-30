package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class showWinnerController {
    @FXML
    private Text winnerText;
    private Stage thisStage; // 이제 스테이지는 필요할 때만 생성됩니다.
    private Scene thisScene;
    private final Lock lock = new ReentrantLock();

    @FXML
    private void exit() {
        Platform.runLater(() -> {
            try {
                GameManager.getInstance().switchToScene("start_menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // 승자를 표시하는 메소드, loser는 패배자의 ID를 나타냄
    public void showWinnerFXML(int loser) {
        lock.lock();
        try {
            Platform.runLater(() -> {
                if (thisStage == null) {
                    initializeStage(); // 스테이지 초기화
                }

                if (thisStage.isShowing()) {
                    return; // 이미 표시되고 있는 경우 종료
                }

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/snust/tetrij/Controller/showWinner.fxml"));
                    Parent root = fxmlLoader.load();
                    thisScene = new Scene(root);
                    thisStage.setScene(thisScene);
                    thisStage.setTitle("Game Over!");

                    showWinnerController controller = fxmlLoader.getController();
                    if (controller.winnerText != null) {
                        switch (loser) {
                            case 0:
                                controller.winnerText.setText("Player2");
                                break;
                            case 1:
                                controller.winnerText.setText("Player1");
                                break;
                            default:
                                controller.winnerText.setText("Draw!");
                                break;
                        }
                    }
                    thisStage.show();

                    // 자동으로 창 닫기를 위한 PauseTransition 설정
                    PauseTransition delay = new PauseTransition(Duration.seconds(5));
                    delay.setOnFinished(event -> {
                        thisStage.close();
                        try {
                            GameManager.getInstance().switchToScene("start_menu.fxml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    delay.play();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            lock.unlock();
        }
    }

    // 스테이지를 초기화하는 내부 메소드
    private void initializeStage() {
        thisStage = new Stage();
        thisStage.initModality(Modality.APPLICATION_MODAL);
    }
}
