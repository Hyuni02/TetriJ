package com.snust.tetrij.GameScene.tetromino;

import javafx.scene.paint.Color;

public class I extends TetrominoBase {
    public I(boolean gen_item) {
        super(gen_item);
        super.name = 'i';
        super.color = Color.BLUE;
        super.mesh = new int[][] {
                {0,0,0,0},
                {1,1,1,1},
                {0,0,0,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, (int)(Math.random() * 6) };
        if (gen_item) {
            genItem();
        }
    }
}
