package com.snust.tetrij.tetromino;

public class J extends TetrominoBase {

    J() {
        super();
        super.pos = new int[] { (int)(Math.random()*100)%7, 0 };
    }
}
