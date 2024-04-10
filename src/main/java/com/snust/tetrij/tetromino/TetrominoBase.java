package com.snust.tetrij.tetromino;

import com.snust.tetrij.Tetris;
import javafx.scene.paint.Color;


public class TetrominoBase {
    public int mesh[][][];
    public Color color;
    public char name;
    public int[] pos; //(y,x)
    public int rotate;

    public TetrominoBase() {
        this.color = new Color(0,0,0,0);
        this.rotate = 0;
    }

    public void update_mesh() {
        for (int y = 0; y < this.mesh[rotate].length; y++) {
            for (int x = 0; x < this.mesh[rotate][y].length; x++) {
                if (this.mesh[rotate][y][x] == 1)
                    Tetris.MESH[y+pos[0]][x+pos[1]] = this.name;
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
        return Color.WHITE;
    }
}
