package com.snust.tetrij.GameScene.tetromino;

import javafx.scene.paint.Color;

public class Boom extends TetrominoBase {
    public Boom(boolean gen_item) {
        super(gen_item);
        super.name = 'B';
        super.color = Color.GREY;
        super.mesh = super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,0},
                {0,1,1,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, (int)(Math.random()*8) };
//        if (gen_item) {
//            genItem();
//        }
    }
}
