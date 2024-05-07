package com.snust.tetrij.GameScene.tetromino;


import javafx.scene.paint.Color;

public class T extends TetrominoBase {
    public T(boolean gen_item) {
        super(gen_item);
        super.name = 't';
        super.color = Color.PURPLE;
        super.mesh = super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,1},
                {0,0,1,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
        if (gen_item) {
            genItem();
        }
    }
}
