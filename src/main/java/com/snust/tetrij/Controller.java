package com.snust.tetrij;

import com.snust.tetrij.tetromino.I;
import com.snust.tetrij.tetromino.J;
import com.snust.tetrij.tetromino.L;
import com.snust.tetrij.tetromino.O;
import com.snust.tetrij.tetromino.S;
import com.snust.tetrij.tetromino.T;
import com.snust.tetrij.tetromino.Z;
import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.scene.shape.Mesh;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Controller {
    public static List<TetrominoBase> bag = new Vector<TetrominoBase>();

    public Controller() { }

    public static char generateTetromino() {
        TetrominoBase t = new TetrominoBase();
        switch((int)(Math.random()*7)) {
            case 1 -> t = new I();
            case 2 -> t = new J();
            case 3 -> t = new L();
            case 4 -> t = new O();
            case 5 -> t = new S();
            case 6 -> t = new T();
            case 0 -> t = new Z();
        }
        bag.add(t);
        t.update_mesh();
        return t.name;
    }

    public static void softDrop(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        eraseMesh(tb);
        tb.pos[0]++;
        tb.update_mesh();
        if (tb.pos[0] >= Tetris.HEIGHT - height) {
            Controller.bag.remove(0);
            eraseLine();
            return;
        }
        for (int x = 0; x < width; x++) {
            if (tb.mesh[tb.rotate][height-1][x] != 1)
                continue;

            if (Tetris.MESH[tb.pos[0] + height][tb.pos[1] + x] != '0') {
                Tetris.top -= height;
                Controller.bag.remove(0);
                eraseLine();
                break;
            }
        }
    }

    public static void hardDrop(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        eraseMesh(Controller.bag.get(0));
        int h = 0;
        boolean can_move_down = true;
        for (; can_move_down; h++) {
            if (tb.pos[0] + h >= Tetris.HEIGHT - height) {
                can_move_down = false;
            }
            for (int x = 0; x < width; x++) {
                if (tb.mesh[tb.rotate][height-1][x] != 1)
                    continue;

                if (Tetris.MESH[tb.pos[0] + height + h - 1][tb.pos[1] + x] != '0') {
                    Tetris.top -= height;
                    can_move_down = false;
                    tb.pos[0]--;
                    break;
                }
            }
        }

        tb.pos[0] += h - 1;
        tb.update_mesh();
        eraseLine();
        Controller.bag.remove(0);
    }

    public static void moveRightOnKeyPress(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        if (tb.pos[1] + width >= Tetris.WIDTH) {
            return;
        }
        // check if right side is not blocked
        for (int y = 0; y < height; y++) {
            if (tb.mesh[tb.rotate][y][width-1] != 1)
                continue;

            if (Tetris.MESH[tb.pos[0]+y][tb.pos[1] + width] != '0') {
                return;
            }
        }

        eraseMesh(tb);
        tb.pos[1]++;
        tb.update_mesh();
    }

    public static void moveLeftOnKeyPress(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.mesh[rot].length;
        int width = tb.mesh[rot][height-1].length;

        if (tb.pos[1] <= 0) {
            return;
        }
        // check if left side is not blocked
        for (int y = 0; y < height; y++) {
            if (tb.mesh[tb.rotate][y][0] != 1)
                continue;

            if (Tetris.MESH[tb.pos[0]+y][tb.pos[1] - 1] != '0') {
                return;
            }
        }

        eraseMesh(tb);
        tb.pos[1]--;
        tb.update_mesh();
    }

    public static void rotateRight(TetrominoBase tb) {
        eraseMesh(tb);
        tb.rotate = tb.rotate != 3 ? ++tb.rotate : 0;
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

    public static void eraseLine() {
        List<Integer> l = new Vector<>();
        for (int y = 2; y < Tetris.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (Tetris.MESH[y][x] == '0') {
                    is_full = false;
                    break;
                }
            }

            if (is_full)
                l.add(y);
        }

        for (int i : l) {
            for (int line = i; line > 2; line--) {
                Tetris.MESH[line] = Tetris.MESH[line-1];
            }
            Tetris.MESH[2] = new char[Tetris.WIDTH];
            Arrays.fill(Tetris.MESH[2], '0');
        }
    }
}

