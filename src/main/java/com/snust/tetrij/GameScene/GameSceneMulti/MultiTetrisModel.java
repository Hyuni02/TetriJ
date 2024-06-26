package com.snust.tetrij.GameScene.GameSceneMulti;

import com.snust.tetrij.tetromino.TetrominoBase;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MultiTetrisModel {
    public static final MultiTetrisModel model = new MultiTetrisModel();

    public int HEIGHT = 21;
    public int WIDTH = 10;
    public char [][] MESH1 = new char[HEIGHT][WIDTH];
    public char [][] MESH2 = new char[HEIGHT][WIDTH];
    public char [][][] MESH = new char[][][] {MESH1, MESH2};
    private char [][] attackBuffer1 = new char[10][WIDTH];
    private char [][] attackBuffer2 = new char[10][WIDTH];
    public char [][][] buffer = new char[][][] {attackBuffer1, attackBuffer2};

    public List<TetrominoBase> bag1 = new Vector<TetrominoBase>();
    public List<TetrominoBase> bag2 = new Vector<TetrominoBase>();
    public List[] bags = new List[2];
    public int buffer_size[] = { 0,0 };
    
    private MultiTetrisModel() {
        initModel();
    }

    public void initModel() {
        for (char[] row : MESH1)
            Arrays.fill(row, '0');
        for (char[] row : MESH2)
            Arrays.fill(row, '0');
        for (char[] row : attackBuffer1)
            Arrays.fill(row, '0');
        for (char[] row : attackBuffer2)
            Arrays.fill(row, '0');

        bag1.clear();
        bag2.clear();

        bags[0] = bag1;
        bags[1] = bag2;

        buffer[0] = attackBuffer1;
        buffer[1] = attackBuffer2;
        buffer[0] = attackBuffer1;
        buffer[1] = attackBuffer2;
        buffer_size[0] = 0;
        buffer_size[1] = 0;
    }
}
