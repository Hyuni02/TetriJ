package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class DeleteAll extends TetrominoBase {
    public DeleteAll() {
        super(false);
        super.name = 'D';
        super.color = Color.BLUE;
        super.mesh = new int[][][] {
                {
                        {1,1}
                },
                {
                        {1},
                        {1}
                },
                {
                        {1,1}
                },
                {
                        {1},
                        {1},
                }
        };
        super.pos = new int[] { 0, 4 };
    }
}
