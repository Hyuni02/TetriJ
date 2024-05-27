package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;

public class Goo extends TetrominoBase{
    public Goo() {
        super(false);
        super.name = 'g';
        super.color = Color.HOTPINK;
        super.mesh = super.mesh = new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        super.pos = new int[]{0, (int) (Math.random() * 8)};
        genItem();
    }

    @Override
    protected void genItem() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (mesh[y][x] == 1) {
                    mesh[y][x] = 6;
                }
            }
        }
    }
}
