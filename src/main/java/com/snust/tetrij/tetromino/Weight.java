package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class Weight extends TetrominoBase {
    public Weight() {
        super(false);
        super.name = 'w';
        super.color = Color.BLACK;
        super.mesh = new int[][] {
                {0,0,0,0},
                {0,1,1,0},
                {1,1,1,1},
                {0,0,0,0}
        };
        super.pos = new int[] { -1, 3 };

    }
}
