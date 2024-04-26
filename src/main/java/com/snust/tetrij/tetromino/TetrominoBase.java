package com.snust.tetrij.tetromino;

import com.snust.tetrij.Controller;
import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;

import javax.sound.sampled.Control;


public class TetrominoBase {
    public int mesh[][][];
    public Color color;
    public char name;
    public int[] pos; //(y,x)
    public int rotate;

    public boolean can_move;

    public TetrominoBase(boolean gen_item) {
        this.color = new Color(0,0,0,0);
        this.rotate = 0;
        this.can_move = true;
    }

    protected void genItem() {
        int block_count = (int)(Math.random()*4);
        for (int rotate = 0; rotate < 4; rotate++) {
            int n = block_count;
            for (int y = 0; y < this.getHeight(rotate); y++) {
                for (int x = 0; x < this.getWidth(rotate); x++) {
                    if (mesh[rotate][y][x] == 1) {
                        n--;
                        if (n == 0)
                            mesh[rotate][y][x] = 2;
                    }
                }
            }
        }

    }

    public void update_mesh() {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (this.mesh[rotate][y][x] == 1)
                    Tetris.MESH[y+pos[0]][x+pos[1]] = this.name;
                else if (this.mesh[rotate][y][x] == 2)
                    Tetris.MESH[y+pos[0]][x+pos[1]] = 'L'; // item mode - Line clear
            }
        }
    }

    public static Color getColor(char name) {
        if (!Tetris.color_weakness) {
            //기본모드
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
                case 'w' -> {
                    return Color.BLACK;
                }
                case 'L' -> {
                    return getColor(Controller.bag.get(0).name);
                }
            };
        }
        else {
            //색약모드
            switch (name) {
                case 'i' -> {
                    return Color.SKYBLUE;
                }
                case 'j' -> {
                    // turquise, 청록
                    return new Color(217.0/256.0, 56.0/256.0, 30.0/256.0, 1);
                }
                case 'l' -> {
                    return Color.ORANGE;
                }
                case 'o' -> {
                    return Color.YELLOW;
                }
                case 's' -> {
                    return Color.GREEN;
                }
                case 't' -> {
                    return Color.BLUE;
                }
                case 'z' -> {
                    //red purple
                    return new Color(149.0/256.0, 53.0/256.0, 83.0/256.0, 1);
                }
                case 'w' -> {
                    return Color.BLACK;
                }
                case 'L' -> {
                    return getColor(Controller.bag.get(0).name);
                }
            };
        }
        return Color.WHITE;
    }

    public int getHeight() {
        return this.mesh[this.rotate].length;
    }

    public int getWidth() {
        return this.mesh[this.rotate][this.getHeight()-1].length;
    }

    public int getHeight(int rotate) {
        return this.mesh[rotate].length;
    }

    public int getWidth(int rotate) {
        return this.mesh[rotate][this.getHeight(rotate)-1].length;
    }
}
