package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class S extends TetrominoBase {
    public S(boolean gen_item) {
        super(gen_item);
        super.name = 's';
        super.color = Color.LIGHTGREEN;
        super.mesh = new int[][][] {
                {
                        {0,1,1},
                        {1,1,0}
                },
                {
                        {1,0},
                        {1,1},
                        {0,1}
                },
                {
                        {0,1,1},
                        {1,1,0}
                },
                {
                        {1,0},
                        {1,1},
                        {0,1}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
        if (gen_item) {
            genItem();
        }
    }
}
