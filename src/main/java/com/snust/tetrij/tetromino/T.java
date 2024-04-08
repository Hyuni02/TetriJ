package com.snust.tetrij.tetromino;


import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

public class T extends TetrominoBase {
    public T() {
        super();
        super.name = 't';
        super.color = Color.PURPLE;
        super.mesh = new int[][][] {
                {
                        {0,1,0},
                        {1,1,1}
                },
                {
                        {1,0},
                        {1,1},
                        {1,0}
                },
                {
                        {1,1,1},
                        {0,1,0}
                },
                {
                        {0,1},
                        {1,1},
                        {0,1}
                }
        };
        super.pos = new int[] { 0, (int)(Math.random()*7) };
    }
}
