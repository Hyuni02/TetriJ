package com.snust.tetrij;

import com.snust.tetrij.tetromino.Base;

import java.util.ArrayList;

public class Generator {
    private static ArrayList<Base> bag;
    public Generator() {

    }

    public static char generate_tetromino() {
        Base t = new Base();
        bag.add(t);
        return t.name;
    }
}
