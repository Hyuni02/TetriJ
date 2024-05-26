package com.snust.tetrij.tetromino;

import com.snust.tetrij.GameScene.GameSceneSingle.SingleBoardController;
import com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel;
import javafx.scene.paint.Color;

import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisModel.model;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;


public class TetrominoBase {
    public int mesh[][];
    public Color color;
    public char name;
    public int[] pos; //(y,x)

    public boolean can_move;

    public TetrominoBase(boolean gen_item) {
        this.color = new Color(0, 0, 0, 0);
        this.can_move = true;
    }

    /**
     * 아이템모드에서 블럭에 'L'을 추가해주는 함수
     */
    protected void genItem() {
        int block_count = (int) (Math.random() * 4);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (mesh[y][x] == 1) {
                    block_count--;
                    if (block_count == 0)
                        mesh[y][x] = 2;
                }
            }
        }
    }

    public void update_mesh(int player) {
        switch(player) {
            case -1:
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (pos[0] + y < 0)
                            continue;

                        if (this.mesh[y][x] == 1)
                            model_s.MESH[y + pos[0]][x + pos[1]] = this.name;
                        else if (this.mesh[y][x] == 2)
                            model_s.MESH[y + pos[0]][x + pos[1]] = 'L'; // item mode - Line clear
                        else if (this.mesh[y][x] == 3)
                            model_s.MESH[y + pos[0]][x + pos[1]] = 'b'; // item mode - boom
                        else if (this.mesh[y][x] == 4)
                            model_s.MESH[y + pos[0]][x + pos[1]] = 'V'; // item mode - Vertical Bomb
                        else if (this.mesh[y][x] == 5)
                            model_s.MESH[y + pos[0]][x + pos[1]] = 'B'; // item mode - Big bomb
                    }
                }
                break;
            default:
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (pos[0] + y < 0)
                            continue;

                        if (this.mesh[y][x] == 1)
                            model.MESH[player][y + pos[0]][x + pos[1]] = this.name;
                        else if (this.mesh[y][x] == 2)
                            model.MESH[player][y + pos[0]][x + pos[1]] = 'L'; // item mode - Line clear
                        else if (this.mesh[y][x] == 3)
                            model.MESH[player][y + pos[0]][x + pos[1]] = 'b'; // item mode - boom
                        else if (this.mesh[y][x] == 4)
                            model.MESH[player][y + pos[0]][x + pos[1]] = 'V'; // item mode - Vertical Bomb
                        else if (this.mesh[y][x] == 5)
                            model.MESH[player][y + pos[0]][x + pos[1]] = 'B'; // item mode - Big bomb
                    }
                }
                break;
        }
    }

    public static Color getColor(char name, int player) {
        if (player == -1){
            if (!controller_s.color_weakness) {
                //기본모드
                switch (name) {
                    case 'i' -> {
                        return Color.SKYBLUE;
                    }
                    case 'j' -> {
                        return Color.BLUE;
                    }
                    case 'l' -> {
                        return Color.ORANGE;
                    }
                    case 'o' -> {
                        return Color.YELLOW;
                    }
                    case 's' -> {
                        return Color.LIGHTGREEN;
                    }
                    case 't' -> {
                        return Color.PURPLE;
                    }
                    case 'z' -> {
                        return Color.RED;
                    }
                    case 'w' -> {
                        return Color.BLACK;
                    }
                    case 'L' -> {
                        return getColor(model_s.bag.get(0).name, -1);
                    }
                    case 'B' -> {
                        return Color.LIGHTGREY;
                    }
                    case 'V' -> {
                        return Color.GREY;
                    }
                    case 'b' -> {
                        return Color.DARKGREY;
                    }
                }
            } else {
                //색약모드
                switch (name) {
                    case 'i' -> {
                        return Color.SKYBLUE;
                    }
                    case 'j' -> {
                        // turquise, 청록
                        return new Color(217.0 / 256.0, 56.0 / 256.0, 30.0 / 256.0, 1);
                    }
                    case 'l' -> {
                        return Color.ORANGE;
                    }
                    case 'o' -> {
                        return Color.YELLOW;
                    }
                    case 's' -> {
                        return Color.GREEN;
                    }
                    case 't' -> {
                        return Color.BLUE;
                    }
                    case 'z' -> {
                        //red purple
                        return new Color(149.0 / 256.0, 53.0 / 256.0, 83.0 / 256.0, 1);
                    }
                    case 'w' -> {
                        return Color.BLACK;
                    }
                    case 'L' -> {
                        return getColor(model_s.bag.get(0).name, -1);
                    }
                    case 'B' -> {
                        return Color.LIGHTGREY;
                    }
                    case 'V' -> {
                        return Color.GREY;
                    }
                    case 'b' -> {
                        return Color.DARKGREY;
                    }
                }
            }
        }
        else {
            if (!controller.color_weakness) {
                //기본모드
                switch (name) {
                    case 'i' -> {
                        return Color.SKYBLUE;
                    }
                    case 'j' -> {
                        return Color.BLUE;
                    }
                    case 'l' -> {
                        return Color.ORANGE;
                    }
                    case 'o' -> {
                        return Color.YELLOW;
                    }
                    case 's' -> {
                        return Color.LIGHTGREEN;
                    }
                    case 't' -> {
                        return Color.PURPLE;
                    }
                    case 'z' -> {
                        return Color.RED;
                    }
                    case 'w' -> {
                        return Color.BLACK;
                    }
                    case 'L' -> {
                        TetrominoBase tb = (TetrominoBase) model.bags[player].get(0);
                        return getColor(tb.name, -1);
                    }
                    case 'B' -> {
                        return Color.LIGHTGREY;
                    }
                    case 'V' -> {
                        return Color.GREY;
                    }
                    case 'b' -> {
                        return Color.DARKGREY;
                    }
                }
            } else {
                //색약모드
                switch (name) {
                    case 'i' -> {
                        return Color.SKYBLUE;
                    }
                    case 'j' -> {
                        // turquise, 청록
                        return new Color(217.0 / 256.0, 56.0 / 256.0, 30.0 / 256.0, 1);
                    }
                    case 'l' -> {
                        return Color.ORANGE;
                    }
                    case 'o' -> {
                        return Color.YELLOW;
                    }
                    case 's' -> {
                        return Color.GREEN;
                    }
                    case 't' -> {
                        return Color.BLUE;
                    }
                    case 'z' -> {
                        //red purple
                        return new Color(149.0 / 256.0, 53.0 / 256.0, 83.0 / 256.0, 1);
                    }
                    case 'w' -> {
                        return Color.BLACK;
                    }
                    case 'L' -> {
                        TetrominoBase tb = (TetrominoBase) model.bags[player].get(0);
                        return getColor(tb.name, -1);
                    }
                    case 'B' -> {
                        return Color.LIGHTGREY;
                    }
                    case 'V' -> {
                        return Color.GREY;
                    }
                    case 'b' -> {
                        return Color.DARKGREY;
                    }
                }
            }
        }
        return Color.WHITE;
    }
}
