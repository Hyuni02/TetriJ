package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MultiTetrisModel {
    public static final MultiTetrisModel model = new MultiTetrisModel();

    public int HEIGHT = 20;
    public int WIDTH = 10;
    public char [][] MESH1 = new char[HEIGHT][WIDTH];
    public char [][] MESH2 = new char[HEIGHT][WIDTH];
    public char [][][] MESH = new char[][][] {MESH1, MESH2};

    public List<TetrominoBase> bag1 = new Vector<TetrominoBase>();
    public List<TetrominoBase> bag2 = new Vector<TetrominoBase>();
    public List[] bags = new List[2];
    
    private MultiTetrisModel() {
        for (char[] row : MESH1)
            Arrays.fill(row, '0');
        for (char[] row : MESH2)
            Arrays.fill(row, '0');

        bags[0] = bag1;
        bags[1] = bag2;
    }

}
