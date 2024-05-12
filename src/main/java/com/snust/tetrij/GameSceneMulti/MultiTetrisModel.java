package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.GameScene.tetromino.TetrominoBase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MultiTetrisModel {
    public static int HEIGHT = 20;
    public static int WIDTH = 10;
    public static char [][] MESH1 = new char[HEIGHT][WIDTH];
    public static char [][] MESH2 = new char[HEIGHT][WIDTH];
    public static char [][][] MESH = new char[][][] {MESH1, MESH2};

    public static List<TetrominoBase> bag1 = new Vector<TetrominoBase>();
    public static List<TetrominoBase> bag2 = new Vector<TetrominoBase>();
    public static List[] bags = new List[2];
    
    public MultiTetrisModel() {
        for (char[] row : MESH1)
            Arrays.fill(row, '0');
        for (char[] row : MESH2)
            Arrays.fill(row, '0');

        bags[0] = bag1;
        bags[1] = bag2;
    }

}
