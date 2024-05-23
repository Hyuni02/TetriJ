package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class J extends TetrominoBase {

    public J(boolean gen_item) {
        super(gen_item);
        super.name = 'j';
        super.color = Color.BLUE;
        super.mesh = super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,1},
                {0,0,0,1},
                {0,0,0,0}
        };
        super.pos = new int[] { -1,3 };
        if (gen_item) {
            genItem();
        }
    }
}
