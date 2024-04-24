package com.snust.tetrij;

import com.snust.tetrij.tetromino.*;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.snust.tetrij.Tetris;

public class Controller {
    public static List<TetrominoBase> bag = new Vector<TetrominoBase>();

    public Controller() { }

    public static void generateTetromino() {
        TetrominoBase t = new TetrominoBase();
        if (!Tetris.item_mode) {
            switch((int)(Math.random()*7)) {
                case 1 -> t = new I();
                case 2 -> t = new J();
                case 3 -> t = new L();
                case 4 -> t = new O();
                case 5 -> t = new S();
                case 6 -> t = new T();
                case 0 -> t = new Z();
            }
        }
        else {
            switch((int)(Math.random()*8)) {
                case 1 -> t = new I();
                case 2 -> t = new J();
                case 3 -> t = new L();
                case 4 -> t = new O();
                case 5 -> t = new S();
                case 6 -> t = new T();
                case 7 -> t = new Z();
                case 0 -> t = new Weight();
            }
            /*int block_count = 4;
            for (int y = 0; y < t.getHeight(); y++) {
                for (int x = 0; x < t.getWidth(); x++) {
                    if (t.mesh[t.rotate][y][x] == 1)
                        block_count--;

                    if (block_count == 0) {
                        t.mesh[t.rotate][y][x] = 2;
                    }
                }
            }*/
        }

        if (!canMoveDown(t, 0)){
            Tetris.isPaused = true;
            return;
        }
        bag.add(t);
        t.update_mesh();
    }


    public static void softDrop(TetrominoBase tb) {
        eraseMesh(tb);
        if (!canMoveDown(tb, 1)) {
            updateTop(tb);
            Controller.bag.remove(0);
        }
        else {
            tb.pos[0]++;
        }

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
        updateTop(tb);
        eraseLine();
        Controller.bag.remove(0);
    }

    public static void moveRightOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        if (canMoveSideWays(tb, 1)) {
            tb.pos[1]++;
        }
        tb.update_mesh();
    }

    public static void moveLeftOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        if (canMoveSideWays(tb, -1)) {
            tb.pos[1]--;
        }
        tb.update_mesh();
    }

    public static void rotateRight(TetrominoBase tb) {
        eraseMesh(tb);
        tb.rotate = tb.rotate != 3 ? ++tb.rotate : 0;
        tb.update_mesh();
    }

    public static void eraseMesh(TetrominoBase tb) {
        int height = tb.getHeight();
        int width = tb.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tb.mesh[tb.rotate][y][x] != 0)
                    Tetris.MESH[tb.pos[0]+y][tb.pos[1]+x] = '0';
            }
        }
    }

    public static void eraseLine() {
        //리스트에 가득 찬 라인을 저장
        List<Integer> lines = new Vector<>();
        for (int y = 2; y < Tetris.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (Tetris.MESH[y][x] == '0') {
                    is_full = false;
                    break;
                }
            }

            if (is_full)
                lines.add(y);
        }

        Tetris.top -= lines.size();
        //리스트에 저장된 라인들을 지움
        for (int i : lines) {
            for (int line = i; line > 2; line--) {
                Tetris.MESH[line] = Tetris.MESH[line-1];
            }
            Tetris.MESH[2] = new char[Tetris.WIDTH];
            Arrays.fill(Tetris.MESH[2], '0');
        }
    }

    public static boolean canMoveDown(TetrominoBase tb, int distance) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        // Tetromino가 아래쪽 경계에 닿았는지 확인
        if (tb.pos[0] + height + distance > Tetris.HEIGHT) {
            return false;
        }
        // 무게추 블록이면 무조건 아래로 내림
        if (tb.name == 'w') {
            return true;
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

    public static boolean canMoveSideWays(TetrominoBase tb, int distance) {
        int rot = tb.rotate;
        int height = tb.getHeight();
        int width = tb.getWidth();

        // Tetromino가 아래쪽 경계에 닿았는지 확인
        if (tb.pos[1] + distance < 0 || tb.pos[1] + width + distance > Tetris.WIDTH) {
            return false;
        }

        // Tetromino의 각 블록이 아래로 이동할 때 다른 블록과 겹치는지 확인
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tb.mesh[rot][y][x] != 1) {
                    continue; // 빈 공간은 확인하지 않음
                }

                // Tetromino의 블록이 아래쪽으로 이동할 때 충돌 여부 확인
                if (Tetris.MESH[tb.pos[0] + y][tb.pos[1] + x + distance] != '0') {
                    return false;
                }
            }
        }
        return true; // 아래로 이동 가능
    }

    private static void updateTop(TetrominoBase tb) {
        Tetris.top = Math.max(Tetris.HEIGHT-tb.pos[0], Tetris.top);
    }
}

