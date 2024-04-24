package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class Weight extends TetrominoBase {
    public Weight() {
        super();
        super.name = 'w';
        super.color = Color.BLACK;
        super.mesh = new int[][][] {
                {
                        {0,1,1,0},
                        {1,1,1,1}
                },
                {
                        {0,1,1,0},
                        {1,1,1,1}
                },
                {
                        {0,1,1,0},
                        {1,1,1,1}
                },
                {
                        {0,1,1,0},
                        {1,1,1,1}
                }
        };
        super.pos = new int[] { 0, 3 };
    }
}
