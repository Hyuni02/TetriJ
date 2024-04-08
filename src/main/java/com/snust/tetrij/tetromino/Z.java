package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class Z extends TetrominoBase {
    public Z() {
        super();
        super.name = 'z';
        super.color = Color.RED;
        super.mesh = new int[][][] {
                {
                        {1,1,0},
                        {0,1,1}
                },
                {
                        {0,1},
                        {1,1},
                        {1,0}
                },
                {
                        {1,1,0},
                        {0,1,1}
                },
                {
                        {0,1},
                        {1,1},
                        {1,0}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
    }
}
