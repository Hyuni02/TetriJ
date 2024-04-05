package com.snust.tetrij;

import com.snust.tetrij.tetromino.Base;

public class Controller {
    public Controller() {

    }

    public static char generate_tetromino() {
        Base t = new Base();
        return t.name;
    }

    public static void moveDown(Base t) {
        t.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(Tetris.ymesh > t.pos[0]+1)
                    return;

                if (Tetris.MESH[y+1][x] != '0' && t.mesh[y][x] != 0){
                    Tetris.MESH[y+t.pos[0]+1][x+t.pos[1]] = t.name;
                }

            }
        }
    }

    public static void moveRight(Base t) {
        t.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(Tetris.xmesh > t.pos[1]+1)
                    return;

                if (Tetris.MESH[y][x+1] != '0' && t.mesh[y][x] != 0){
                    Tetris.MESH[y+t.pos[0]][x+t.pos[1]+1] = t.name;
                }

            }
        }
    }

    public static void moveLeft(Base t) {
        t.pos[1]++;
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                if(t.pos[1] < 0)
                    return;

                if (Tetris.MESH[y+1][x] != '0' && t.mesh[y][x] != 0){
                    Tetris.MESH[y+t.pos[0]][x+t.pos[1]] = t.name;
                }

            }
        }
    }
}

