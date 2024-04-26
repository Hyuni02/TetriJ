package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class Boom extends TetrominoBase {
    public Boom(boolean gen_item) {
        super(gen_item);
        super.name = 'B';
        super.color = Color.GREY;
        super.mesh = new int[][][] {
                {
                        {1,1},
                        {1,1}
                },
                {
                        {1,1},
                        {1,1}
                },
                {
                        {1,1},
                        {1,1}
                },
                {
                        {1,1},
                        {1,1}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random()*8) };
//        if (gen_item) {
//            genItem();
//        }
    }
}
