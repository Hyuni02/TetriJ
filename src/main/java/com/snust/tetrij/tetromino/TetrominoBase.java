package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;


public class TetrominoBase {
    public int mesh[][];
    protected int pos_trans[][];
    public Color color;
    public char name;
    public int turned;
    public int[] pos;
    public TetrominoBase() {
        this.color = new Color(0,0,0,0);
        this.turned = 0;
        this.pos = new int[] { 0, (int)(Math.random()*100)%7 }; //(y,x)
        switch ((int)(Math.random()*100)%7) {
            case 1 -> {
                name = 'i';
                color = Color.SKYBLUE;
                mesh = new int[][] {
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0}
                };
            }
            case 2 -> {
                name = 'j';
                color = Color.BLUE;
                mesh = new int[][] {
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0}
                };
            }
            case 3 -> {
                name = 'l';
                color = Color.ORANGE;
                mesh = new int[][] {
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0}
                };
            }
            case 4 -> {
                name = 'o';
                color = Color.YELLOW;
                mesh = new int[][] {
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0}
                };
            }
            case 5 -> {
                name = 's';
                color = Color.LIGHTGREEN;
                mesh = new int[][] {
                        {0, 0, 0, 0},
                        {0, 0, 1, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0}
                };
            }
            case 6 -> {
                name = 't';
                color = Color.PURPLE;
                mesh = new int[][] {
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 1, 0, 0},
                        {0, 0, 0, 0}
                };
            }
            case 0 -> {
                name = 'z';
                color = Color.RED;
                mesh = new int[][] {
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 1, 1},
                        {0, 0, 0, 0}
                };
            }
        }
    }

    public void rotate_right() {
        mesh = new int[][] {
                {mesh[0][3], mesh[1][3], mesh[2][3], mesh[3][3]},
                {mesh[0][2], mesh[1][2], mesh[2][2], mesh[3][2]},
                {mesh[0][1], mesh[1][1], mesh[2][1], mesh[3][1]},
                {mesh[0][0], mesh[1][0], mesh[2][0], mesh[3][0]}
        };
        turned++;
    }

    public void rotate_left() {
        mesh = new int[][] {
                {mesh[3][0], mesh[2][0], mesh[1][0], mesh[0][0]},
                {mesh[3][1], mesh[2][1], mesh[1][1], mesh[0][1]},
                {mesh[3][2], mesh[2][2], mesh[1][2], mesh[0][2]},
                {mesh[3][3], mesh[2][3], mesh[1][3], mesh[0][3]}
        };
        turned--;
    }

    public void update_mesh() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Tetris.MESH[y+pos_trans[turned][0]][x+pos_trans[turned][1]]
                        = this.mesh[y][x] == 1 ? name : '0';
            }
        }
    }

    public static Color getColor(char name) {
        switch (name) {
            case 'i' -> {
                return Color.SKYBLUE;
            }
            case 'j' -> {
                return Color.BLUE;
            }
            case 'l' -> {
                return Color.ORANGE;
            }
            case 'o' -> {
                return Color.YELLOW;
            }
            case 's' -> {
                return Color.LIGHTGREEN;
            }
            case 't' -> {
                return Color.PURPLE;
            }
            case 'z' -> {
                return Color.RED;
            }
        };
        return null;
    }
}
