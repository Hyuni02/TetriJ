package com.snust.tetrij;

import java.util.ArrayList;

public class Generator {
    private static ArrayList<Tetromino> bag;
    public Generator() {

    }

    public static char generate_tetromino() {
        Tetromino t = new Tetromino();
        bag.add(t);
        return t.name;
    }
}
