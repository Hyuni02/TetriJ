package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;

public class I extends TetrominoBase {

    I() {
        super();
        super.pos_trans = new int[][]{
                {0,2},
                {2,0},
                {0,1},
                {1,0}
        };
        super.pos = new int[] { (int)(Math.random()*100)%(Tetris.xmesh-pos_trans[0][0]), 0 };
    }
}
