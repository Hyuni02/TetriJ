package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class DeleteAll extends TetrominoBase {
    public DeleteAll() {
        super(false);
        super.name = 'D';
        super.color = Color.BLUE;
        super.mesh = super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,0},
                {0,1,1,0},
                {0,0,0,0}
        };
        super.pos = new int[] { 0, 4 };
    }
}
