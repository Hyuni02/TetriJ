package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class BigBomb extends TetrominoBase{
    public BigBomb() {
        super(false);
        super.name = 'B';
        super.color = Color.LIGHTGREY;
        super.mesh = super.mesh = new int[][]{
                {0, 1, 1, 0},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 0}
        };
        super.pos = new int[]{0, (int) (Math.random() * 8)};
        genItem();
    }

    @Override
    protected void genItem() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (mesh[y][x] == 1) {
                    mesh[y][x] = 5;
                }
            }
        }
    }
}
