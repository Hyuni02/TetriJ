package com.snust.tetrij;

import javafx.scene.paint.Color;

public class Tetromino {
    private Color color;
    private char name;

    public int turned;
    public Tetromino(Color color) {
        this.color = color;
        this.turned = 0;
        int random_var = (int)(Math.random()*100)%7;
        switch (name) {
            case 1:
                name = 'i';
                break;
            case 2:
                name = 'o';
                break;
            case 3:
                name = 'z';
                break;
            case 4:
                name = 's';
                break;
            case 5:
                name = 'j';
                break;
            case 6:
                name = 'l';
                break;
            case 0:
                name = 't';
                break;
        }
    }

    public void fill_mesh() {
        switch (name) {
            case 'i':
                break;
            case 'o':
                break;
            case 'z':
                break;
            case 's':
                break;
            case 'j':
                break;
            case 'l':
                break;
            case 't':
                break;
        }
    }
}
