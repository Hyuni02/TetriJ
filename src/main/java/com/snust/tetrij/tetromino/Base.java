package com.snust.tetrij.tetromino;

import javafx.scene.paint.Color;


public class Base {
    public int mesh[][];
    protected int pos_trans[][];
    public Color color;
    public char name;
    public int turned;
    public int[] pos;
    public Base() {
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


    public void color_mesh() { }

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
}
