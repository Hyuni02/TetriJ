package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class I extends TetrominoBase {
    public I() {
        super();
        super.name = 'i';
        super.color = Color.BLUE;
        super.mesh = new int[][][] {
                {
                        {1,1,1,1}
                },
                {
                        {1},
                        {1},
                        {1},
                        {1}
                },
                {
                        {1,1,1,1}
                },
                {
                        {1},
                        {1},
                        {1},
                        {1}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random() * 6) };
    }
}
