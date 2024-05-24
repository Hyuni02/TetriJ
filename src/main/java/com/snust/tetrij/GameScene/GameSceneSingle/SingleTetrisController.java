package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.MultiTetris;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController extends GameControllerBase {
    public final static SingleTetrisController controller_s = new SingleTetrisController();
    PlayerThreadSingle playerThread;

    public SingleTetrisController() {
        super();
    }


    public void runGame(Stage stage, difficulty difficulty) {
        view_s.initView();
        model_s.initModel();

        view_s.setScene();
        view_s.stage = stage;
        currentDifficulty = difficulty;

        view_s.stage.setOnCloseRequest(event->{
            controller_s.playerThread.interrupt();
        });


        SingleKeyController.addListenerGameControl(view_s.scene);

        playerThread = new PlayerThreadSingle("Single Play");
        playerThread.start();
    }

    public void togglePause() {
        System.out.println("Pause");
        Platform.runLater( () -> {
            if (!controller_s.onPauseButton) {
                controller_s.isPaused = true;
                controller_s.onPauseButton = !controller_s.isPaused;
                if (controller_s.isPaused) {
                    try{
                        controller_s.onPauseButton = true;
                        FXMLLoader fxmlLoader = new FXMLLoader(MultiTetris.class.getResource("pause_menu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage pauseStage = new Stage();
                        pauseStage.setScene(new Scene(root));
                        pauseStage.setTitle("Pause");
                        pauseStage.setOnCloseRequest(
                                event -> {
                                    //pause 창이 닫힐 때
                                    controller_s.isPaused = false;
                                    controller_s.onPauseButton = false;
                                }
                        );
                        pauseStage.getScene().setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ESCAPE) {
                                pauseStage.close();
                                Platform.exit();
                            }
                        });
                        pauseStage.showAndWait();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

    }
}
