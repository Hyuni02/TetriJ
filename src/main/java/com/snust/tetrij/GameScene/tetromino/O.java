package com.snust.tetrij.GameScene.tetromino;

import javafx.scene.paint.Color;

public class O extends TetrominoBase {
    public O(boolean gen_item) {
        super(gen_item);
        super.name = 'o';
        super.color = Color.YELLOW;
        super.mesh = super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,0},
                {0,1,1,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, (int)(Math.random()*8) };
        if (gen_item) {
            genItem();
        }
    }
}
