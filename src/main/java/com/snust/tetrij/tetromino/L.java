package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class L extends TetrominoBase {
    public L(boolean gen_item) {
        super(gen_item);
        super.name = 'l';
        super.color = Color.ORANGE;
        super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,1},
                {0,1,0,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
        if (gen_item) {
            genItem();
        }
    }
}
