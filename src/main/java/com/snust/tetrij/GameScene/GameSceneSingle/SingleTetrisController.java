package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameControllerBase;
import javafx.stage.Stage;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class SingleTetrisController extends GameControllerBase {
    public final static SingleTetrisController controller_s = new SingleTetrisController();

    public SingleTetrisController() {
        super();
    }


    public void runGame(Stage stage, difficulty difficulty) {
        view_s.setScene();
        view_s.stage = stage;
        currentDifficulty = difficulty;

        SingleKeyController.addListenerGameControl(view_s.scene);

        PlayerThreadSingle playerThread = new PlayerThreadSingle("Single Play");
        playerThread.start();
    }
}
