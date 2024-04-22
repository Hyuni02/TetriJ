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

    public static boolean canMoveDown(TetrominoBase tb, int distance) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        // Tetromino가 아래쪽 경계에 닿았는지 확인
        if (tb.pos[0] + height + distance > Tetris.HEIGHT) {
            return false;
        }

        // Tetromino의 각 블록이 아래로 이동할 때 다른 블록과 겹치는지 확인
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tb.mesh[rot][y][x] != 1) {
                    continue; // 빈 공간은 확인하지 않음
                }

                // Tetromino의 블록이 아래쪽으로 이동할 때 충돌 여부 확인
                if (Tetris.MESH[tb.pos[0] + y + distance][tb.pos[1] + x] != '0') {
                    return false;
                }
            }
        }

        return true; // 아래로 이동 가능
    }


    public static void softDrop(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        eraseMesh(tb);
        int dropHeight = 1;
        if (!canMoveDown(tb, dropHeight)) {
            Controller.bag.remove(0);
            return;
        }

        tb.pos[0]++;
        tb.update_mesh();
        eraseLine();
    }

    public static void hardDrop(TetrominoBase tb) {
        eraseMesh(tb);
        int dropHeight = 0;
        while (canMoveDown(tb, dropHeight + 1)) {
            dropHeight++;
        }

        tb.pos[0] += dropHeight;
        tb.update_mesh();
        eraseLine();
        Controller.bag.remove(0);
    }

    public static void moveRightOnKeyPress(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        //오른쪽으로 이동할 때 범위를 벗어나는지 확인
        if (tb.pos[1] + width >= Tetris.WIDTH) {
            return;
        }
        // Tetromino의 각 블록이 오른쪽으로 이동할 때 다른 블록과 겹치는지 확인
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tb.mesh[rot][y][x] != 1) {
                    continue; // 빈 공간은 확인하지 않음
                }

                // Tetromino의 블록이 오른쪽으로 이동할 때 충돌 여부 확인
                if (Tetris.MESH[tb.pos[0] + y][tb.pos[1] + x + 1] != '0') {
                    return;
                }
            }
        }

        eraseMesh(tb);
        tb.pos[1]++;
        tb.update_mesh();
    }

    public static void moveLeftOnKeyPress(TetrominoBase tb) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        //왼쪽 경계를 넘어가는지 확인
        if (tb.pos[1] <= 0) {
            return;
        }
        // Tetromino의 각 블록이 왼쪽으로 이동할 때 다른 블록과 겹치는지 확인
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tb.mesh[rot][y][x] != 1) {
                    continue; // 빈 공간은 확인하지 않음
                }

                // Tetromino의 블록이 오른쪽으로 이동할 때 충돌 여부 확인
                if (Tetris.MESH[tb.pos[0] + y][tb.pos[1] + x - 1] != '0') {
                    return;
                }
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
        int height = tb.getHeight();
        int width = tb.getWidth();

        for (int y = tb.pos[0]; y < tb.pos[0] + height; y++) {
            for (int x = tb.pos[1]; x < tb.pos[1] + width; x++) {
                Tetris.MESH[y][x] = '0';
            }
        }
    }

    public static void eraseLine() {
        //리스트에 가득 찬 라인을 저장
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

        //리스트에 저장된 라인들을 지움
        for (int i : l) {
            for (int line = i; line > 2; line--) {
                Tetris.MESH[line] = Tetris.MESH[line-1];
            }
            Tetris.MESH[2] = new char[Tetris.WIDTH];
            Arrays.fill(Tetris.MESH[2], '0');
        }
    }
}

