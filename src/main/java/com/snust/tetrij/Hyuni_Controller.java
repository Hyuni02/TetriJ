package com.snust.tetrij;

import javafx.scene.shape.Rectangle;

public class Hyuni_Controller {
    public static final int MOVE = Hyuni_Tetris.MOVE;
    public static final int SIZE = Hyuni_Tetris.SIZE;
    public static int XMAX = Hyuni_Tetris.XMAX;
    public static int YMAX = Hyuni_Tetris.YMAX;
    public static int[][] MESH = Hyuni_Tetris.MESH;

    public static void MoveRight(Hyuni_Form form) {
        if (form.a.getX() + MOVE <= XMAX - SIZE
                && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE
                && form.d.getX() + MOVE <= XMAX - SIZE) {
            int movea = MESH[((int) form.a.getX() / SIZE) + 1][((int) form.a.getX() / SIZE) + 1];
            int moveb = MESH[((int) form.b.getX() / SIZE) + 1][((int) form.b.getX() / SIZE) + 1];
            int movec = MESH[((int) form.c.getX() / SIZE) + 1][((int) form.c.getX() / SIZE) + 1];
            int moved = MESH[((int) form.d.getX() / SIZE) + 1][((int) form.d.getX() / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    public static void MoveLeft(Hyuni_Form form) {
        if (form.a.getX() - MOVE >= 0
                && form.b.getX() - MOVE >= 0
                && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int movea = MESH[((int) form.a.getX() / SIZE) - 1][((int) form.a.getX() / SIZE) - 1];
            int moveb = MESH[((int) form.b.getX() / SIZE) - 1][((int) form.b.getX() / SIZE) - 1];
            int movec = MESH[((int) form.c.getX() / SIZE) - 1][((int) form.c.getX() / SIZE) - 1];
            int moved = MESH[((int) form.d.getX() / SIZE) - 1][((int) form.d.getX() / SIZE) - 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    public static Hyuni_Form makeRect() {
        int block = (int) (Math.random() * 100);
        String name;

        Rectangle a = new Rectangle(SIZE - 1, SIZE - 1),
                b = new Rectangle(SIZE - 1, SIZE - 1),
                c = new Rectangle(SIZE - 1, SIZE - 1),
                d = new Rectangle(SIZE - 1, SIZE - 1);

        if (block < 15) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "j";
        } else if (block < 30) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "l";
        } else if (block < 45) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            name = "o";
        } else if (block < 60) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            name = "s";
        } else if (block < 75) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            name = "t";
        } else if (block < 90) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            name = "z";
        } else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
            name = "i";
        }
        return new Hyuni_Form(a,b,c,d,name);
    }
}
