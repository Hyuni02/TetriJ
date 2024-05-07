package com.snust.tetrij.GameScene.tetromino;

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
        super.pos = new int[] { 0, (int)(Math.random()*7) };
        if (gen_item) {
            genItem();
        }
    }
}
