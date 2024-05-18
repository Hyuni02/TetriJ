package com.snust.tetrij.GameSceneSingle;

import com.snust.tetrij.tetromino.TetrominoBase;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class SingleTetrisModel {
    public static final SingleTetrisModel model_s = new SingleTetrisModel();

    public int HEIGHT = 20;
    public int WIDTH = 10;
    public char [][] MESH = new char[HEIGHT][WIDTH];

    public List<TetrominoBase> bag = new Vector<TetrominoBase>();
    private SingleTetrisModel() {
        for (char[] row : MESH)
            Arrays.fill(row, '0');
    }

}
