package com.snust.tetrij;

import com.snust.tetrij.tetromino.TetrominoBase;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Vector;

import static com.snust.tetrij.Tetris.MESH;
import static com.snust.tetrij.Tetris.pane;

public class Controller {
    public static List<TetrominoBase> bag = new Vector<TetrominoBase>();

    public Controller() { }

    public static char generate_tetromino() {
        TetrominoBase t = new TetrominoBase();
        bag.add(t);
        return t.name;
    }

    public static void moveDownOnKeyPress(TetrominoBase b) {
        b.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(Tetris.ymesh > b.pos[0]+1)
                    return;

                if (MESH[y+1][x] != '0' && b.mesh[y][x] != 0){
                    MESH[y+b.pos[0]+1][x+b.pos[1]] = b.name;
                }

            }
        }
    }

    public static void moveRightOnKeyPress(TetrominoBase b) {
        b.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(Tetris.xmesh > b.pos[1]+1)
                    return;

                if (MESH[y][x+1] != '0' && b.mesh[y][x] != 0){
                    MESH[y+b.pos[0]][x+b.pos[1]+1] = b.name;
                }

            }
        }
    }

    public static void moveLeftOnKeyPress(TetrominoBase b) {
        b.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(b.pos[1] < 0)
                    return;

                if (MESH[y+1][x] != '0' && b.mesh[y][x] != 0){
                    MESH[y+b.pos[0]][x+b.pos[1]] = b.name;
                }

            }
        }
    }

    public static void moveDownPerSec() {
        for (int y = Tetris.ymesh-1; y > 0; y--) {
            for (int x = Tetris.xmesh-1; x > 0; x--) {
                if (MESH[y][x] == '0') {
                    MESH[y][x] = MESH[y-1][x-1];
                }

            }
        }
    }
}

