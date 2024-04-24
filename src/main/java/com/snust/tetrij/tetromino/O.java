package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class O extends TetrominoBase {
    public O() {
        super();
        super.name = 'o';
        super.color = Color.YELLOW;
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
    }
}
