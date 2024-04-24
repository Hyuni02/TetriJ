package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class L extends TetrominoBase {
    public L() {
        super();
        super.name = 'l';
        super.color = Color.ORANGE;
        super.mesh = new int[][][] {
                {
                        {0,0,1},
                        {1,1,1}
                },
                {
                        {1,0},
                        {1,0},
                        {1,1}
                },
                {
                        {1,1,1},
                        {1,0,0}
                },
                {
                        {1,1},
                        {0,1},
                        {0,1}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
    }
}
