package com.snust.tetrij;

import com.snust.tetrij.tetromino.I;
import com.snust.tetrij.tetromino.J;
import com.snust.tetrij.tetromino.L;
import com.snust.tetrij.tetromino.O;
import com.snust.tetrij.tetromino.S;
import com.snust.tetrij.tetromino.T;
import com.snust.tetrij.tetromino.Z;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.snust.tetrij.Tetris;

public class Controller {
    public static List<TetrominoBase> bag = new Vector<TetrominoBase>();

    public Controller() { }

    public static char generate_tetromino() {
        TetrominoBase t = new TetrominoBase();
        switch((int)(Math.random()*7)) {
            case 1 -> {
                t = new I();
            }
            case 2 -> {
                t = new J();
            }
            case 3 -> {
                t = new L();
            }
            case 4 -> {
                t = new O();
            }
            case 5 -> {
                t = new S();
            }
            case 6 -> {
                t = new T();

            }
            case 0 -> {
                t = new Z();
            }
        }
        bag.add(t);
        t.update_mesh();
        return t.name;
    }

    public static void moveDownPerSec(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        eraseMesh(tb);
        tb.pos[0]++;
        tb.update_mesh();
        if (tb.pos[0] >= Tetris.HEIGHT - height) {
            Controller.bag.remove(0);
            return;
        }
        for (int x = 0; x < width; x++) {
            if (Tetris.MESH[tb.pos[0] + height][tb.pos[1] + x] != '0') {
                Controller.bag.remove(0);
                return;
            }
        }
    }

    public static void moveDownOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        if (height + tb.pos[0] > Tetris.HEIGHT) {
            return;
        }
        for (int x = 0; x < width; x++) {
            if (Tetris.MESH[tb.pos[0] + height][tb.pos[1] + x] != '0') {
                Controller.bag.remove(0);
                return;
            }
        }

        tb.pos[0]++;
        tb.update_mesh();
        if (tb.pos[0] >= Tetris.HEIGHT - height - 1) {
            Controller.bag.remove(0);
            return;
        }
    }

    public static void moveRightOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;
        if (tb.pos[1] + width < Tetris.WIDTH) {
            tb.pos[1]++;
        }
        tb.update_mesh();
    }

    public static void moveLeftOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;
        if (tb.pos[1] > 0) {
            tb.pos[1]--;
        }
        tb.update_mesh();
    }

    public static void eraseMesh(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        for (int y = tb.pos[0]; y < tb.pos[0] + height; y++) {
            for (int x = tb.pos[1]; x < tb.pos[1] + width; x++) {
                Tetris.MESH[y][x] = '0';
            }
        }
    }
}

