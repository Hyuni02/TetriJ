package com.snust.tetrij.GameScene;

import com.snust.tetrij.Tetris;
import com.snust.tetrij.tetromino.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

public class TetrisBoardController {
    public static List<TetrominoBase> bag = new Vector<TetrominoBase>();

    public TetrisBoardController() {
    }

    public static int RWS(Tetris.difficulty dif) {
        double[] fitnesses = null;
        switch (dif) {
            case EASY -> {
                fitnesses = new double[]{1, 1, 1, 1.2, 1, 1, 1};  //easy난이도에서는 L블럭 20퍼센트 더 많이
            }
            case HARD -> {
                fitnesses = new double[]{1, 1, 1, 0.8, 1, 1, 1};
            }
            case ITEM -> {
                fitnesses = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            }
            default -> {
                // normal 혹은 item
                fitnesses = new double[]{1, 1, 1, 1, 1, 1, 1};
            }
        }

        double totalFitness = 0;
        // 모든 적합도의 합을 계산
        for (double fitness : fitnesses) {
            totalFitness += fitness;
        }

        // 랜덤값을 기준으로 선택할 위치 결정
        Random rand = new Random();
        double value = rand.nextDouble() * totalFitness;

        // 룰렛 휠 시뮬레이션
        double runningTotal = 0;
        for (int i = 0; i < fitnesses.length; i++) {
            runningTotal += fitnesses[i];
            if (runningTotal >= value) {
                return i;
            }
        }

        // 모든 값이 0인 경우 마지막 인덱스 반환
        return fitnesses.length - 1;
    }

    public static void generateTetromino() {
        TetrominoBase t = new TetrominoBase(false);
        int idx = RWS(Tetris.cur_dif);
        if (Tetris.cur_dif != Tetris.difficulty.ITEM) {
            switch (idx) {
                case 0 -> t = new Z(false);
                case 1 -> t = new I(false);
                case 2 -> t = new J(false);
                case 3 -> t = new L(false);
                case 4 -> t = new O(false);
                case 5 -> t = new S(false);
                case 6 -> t = new T(false);
            }
        } else {
            if (Tetris.deleted_lines <= 2) {
                Tetris.deleted_lines = 0;
                switch (idx) {
                    case 0 -> t = new Z(true);
                    case 1 -> t = new I(true);
                    case 2 -> t = new J(true);
                    case 3 -> t = new L(true);
                    case 4 -> t = new O(true);
                    case 5 -> t = new S(true);
                    case 6 -> t = new T(true);
                    case 7 -> t = new Boom();
                    case 8 -> t = new BigBomb();
                    case 9 -> t = new VerticalBomb();
                    case 10 -> t = new Weight();
                }
            } else {
                switch (idx) {
                    case 0 -> t = new Z(false);
                    case 1 -> t = new I(false);
                    case 2 -> t = new J(false);
                    case 3 -> t = new L(false);
                    case 4 -> t = new O(false);
                    case 5 -> t = new S(false);
                    case 6 -> t = new T(false);
                }
            }

        }

        if (!canMoveDown(t, 0)) {
            Tetris.isPaused = true;
            return;
        }

        bag.add(t);

        int start_pos_y = 0;
        for (int[] y : t.mesh) {
            boolean is_empty = true;
            for (int x : y) {
                if (x != 0)
                    is_empty = false;
            }

            if (!is_empty) {
                break;
            }
            start_pos_y++;
        }

        t.pos[0] -= start_pos_y;
        //여기 꼭 수정할것!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void softDrop(TetrominoBase tb) {
        eraseMesh(tb);
        tb.pos[0]++;
        if (!canMoveDown(tb, 1)) {
            updateTop(tb);
            tb.update_mesh(-1);
            eraseLine();
            if (tb.name == 'b') explosion(tb);
            if (tb.name == 'V') verticalExplosion(tb);
            if (tb.name == 'B') bigExplosion(tb);

            TetrisBoardController.bag.remove(0);
            generateTetromino();
            return;
        }
        if (tb.name == 'w') {
            if (!isClearBelow((Weight) tb)) {
                tb.can_move = false;
            }
        }
        tb.update_mesh(-1);
    }

    //무게추가 좌우 고정이 되어야하는지 확인만을 위한 함수
    public static boolean isClearBelow(Weight tb) {
        if (canMoveDown(tb, 0)) {
            return true;
        }
        return false;
    }

    //Boom이 바닥에 닿았을 때 4x4를 지우는 함수
    public static void explosion(TetrominoBase tb) {
        int left = tb.pos[1];
        int top = tb.pos[0];
        for (int y = top; y < top + 4; y++) {
            if (y > Tetris.HEIGHT - 1 || y < 0) {
                continue;
            }
            for (int x = left; x < left + 4; x++) {
                if (x < 0 || x > Tetris.WIDTH - 1) {
                    continue;
                }
                Tetris.MESH[y][x] = '0';

                //리스트에 저장된 블록들을 지움
                int finalX = x;
                int finalY = y;
                Task<Void> eraseTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            highlightBlock(finalX, finalY); //삭제되는 블록색 바꾸기
                        });
                        return null;
                    }
                };
                Thread eraseThread = new Thread(eraseTask);
                eraseThread.setDaemon(true);
                eraseThread.start();
            }
        }
    }

    public static void bigExplosion(TetrominoBase tb) {
        List<Integer> l = new Vector<>();
        for (int y = 0; y < Tetris.HEIGHT; y++) {
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (Tetris.MESH[y][x] != '0') {
                    l.add(y);
                }
            }

            Task<Void> eraseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int line : l) {
                        Platform.runLater(() -> {
                            highlightLine(line); //삭제되는 블록색 바꾸기
                        });
                        Platform.runLater(() -> {
                            // 라인 지우기
                            for (int l = line; l > 2; l--) {
                                Tetris.MESH[l] = Tetris.MESH[l - 1];  //블록 당기기
                            }
                            Tetris.MESH[2] = new char[Tetris.WIDTH];
                            Arrays.fill(Tetris.MESH[2], '0');
                        });
                    }
                    return null;
                }
            };
            Thread eraseThread = new Thread(eraseTask);
            eraseThread.setDaemon(true);
            eraseThread.start();
        }
    }

    public static void verticalExplosion(TetrominoBase tb) {
        int left = tb.pos[1] + 1;
        int right = tb.pos[1] + 2;
        for (int y = 0; y < Tetris.HEIGHT; y++) {
            Tetris.MESH[y][left] = '0';
            Tetris.MESH[y][right] = '0';

            //리스트에 저장된 블록들을 지움
            int finalY = y;
            Task<Void> eraseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> {
                        highlightBlock(left, finalY); //삭제되는 블록색 바꾸기
                        highlightBlock(right, finalY);
                    });
                    return null;
                }
            };
            Thread eraseThread = new Thread(eraseTask);
            eraseThread.setDaemon(true);
            eraseThread.start();
        }
    }

    public static void hardDrop(TetrominoBase tb) {
        eraseMesh(tb);
        int dropHeight = 0;
        while (canMoveDown(tb, dropHeight + 1)) {
            dropHeight++;
        }

        tb.pos[0] += dropHeight;
        tb.update_mesh(-1);
        updateTop(tb);
        eraseLine();
        if (tb.name == 'b') explosion(tb);
        if (tb.name == 'V') verticalExplosion(tb);
        if (tb.name == 'B') bigExplosion(tb);
        TetrisBoardController.bag.remove(0);
        generateTetromino();
    }

    public static void moveRightOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        if (canMoveSideWays(tb, 1) && tb.can_move) {
            tb.pos[1]++;
        }
        tb.update_mesh(-1);
    }

    public static void moveLeftOnKeyPress(TetrominoBase tb) {
        eraseMesh(tb);
        if (canMoveSideWays(tb, -1) && tb.can_move) {
            tb.pos[1]--;
        }
        tb.update_mesh(-1);
    }

    public static void rotateClockWise(TetrominoBase tb) {
        eraseMesh(tb);
        int[][] rotated = canRotateClockwise(tb);
        if (rotated != null) {
            tb.mesh = rotated;
        }
        tb.update_mesh(-1);
    }

    public static boolean canMoveDown(TetrominoBase tb, int distance) {
        // 블록의 아래쪽에 다른 블록이 있는지 확인
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tb.mesh[y][x] == 0) {
                    continue;
                }
                if (y + tb.pos[0] + distance >= Tetris.HEIGHT) {
                    return false;
                }
                if (Tetris.MESH[y + tb.pos[0] + distance][x + tb.pos[1]] != '0') {
                    if (tb.name == 'w')
                        continue;
                    else
                        return false;
                }

            }
        }
        return true;
    }

    public static boolean canMoveSideWays(TetrominoBase tb, int distance) {
        // Tetromino의 각 블록이 아래로 이동할 때 다른 블록과 겹치는지 확인
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tb.mesh[y][x] == 0) {
                    continue; // 빈 공간은 확인하지 않음
                }
                // Tetromino의 블록이 밖으로 나가는지 확인
                if (x + tb.pos[1] + distance < 0 || x + tb.pos[1] + distance >= Tetris.WIDTH) {
                    return false;
                }
                // Tetromino의 블록이 측면으로 이동할 때 충돌 여부 확인
                if (Tetris.MESH[tb.pos[0] + y][tb.pos[1] + x + distance] != '0') {
                    return false;
                }
            }
        }
        return true; // 아래로 이동 가능
    }

    public static int[][] canRotateClockwise(TetrominoBase tb) {
        // 회전 후의 상태를 가상으로 생성하여 유효성을 검사
        int[][] rotatedShape = new int[4][4];

        // 회전 후의 모양을 생성
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                rotatedShape[y][x] = tb.mesh[4 - 1 - x][y];
            }
        }

        // 유효성 검사
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (rotatedShape[y][x] == 1) {
                    // 회전 후의 위치가 보드를 벗어나는 경우
                    if (y >= Tetris.HEIGHT || x >= Tetris.WIDTH) {
                        return null;
                    }
                    // 회전 후의 위치에 이미 다른 블록이 있는 경우
                    if (Tetris.MESH[y + tb.pos[0]][x + tb.pos[1]] == 1) {
                        return null;
                    }
                }
            }
        }
        return rotatedShape;
    }

    public static void eraseMesh(TetrominoBase tb) {
        for (int y = tb.pos[0]; y < tb.pos[0] + 4; y++) {
            for (int x = tb.pos[1]; x < tb.pos[1] + 4; x++) {
                if (y >= Tetris.HEIGHT || y < 0)
                    continue;
                if (x >= Tetris.WIDTH || x < 0)
                    continue;

                if (tb.mesh[y - tb.pos[0]][x - tb.pos[1]] != 0)
                    Tetris.MESH[y][x] = '0';
            }
        }
    }

    public static void eraseLine() {
        //리스트에 가득 찬 라인을 저장
        List<Integer> l = new Vector<>();
        for (int y = 2; y < Tetris.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (Tetris.MESH[y][x] == 'L') {
                    is_full = true;
                    break;
                }
                if (Tetris.MESH[y][x] == '0') {
                    is_full = false;
                }
            }
            if (is_full)
                l.add(y);
        }
        Tetris.top -= l.size();
        Tetris.deleted_lines += l.size();

        if (l.isEmpty())
            return;

        //리스트에 저장된 라인들을 지움
        Task<Void> eraseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int line : l) {
                    Platform.runLater(() -> {
                        highlightLine(line); //삭제되는 블록색 바꾸기
                    });
                    Platform.runLater(() -> {
                        // 라인 지우기
                        for (int l = line; l > 2; l--) {
                            Tetris.MESH[l] = Tetris.MESH[l - 1];  //블록 당기기
                        }
                        Tetris.MESH[2] = new char[Tetris.WIDTH];
                        Arrays.fill(Tetris.MESH[2], '0');
                        Tetris.score += 50;
                        Tetris.linesNo++;
                        Tetris.changeSpeed();
                    });
                }
                return null;
            }
        };
        Thread eraseThread = new Thread(eraseTask);
        eraseThread.setDaemon(true);
        eraseThread.start();
    }

    /*
    public static void eraseLine() {
        //리스트에 가득 찬 라인을 저장
        List<Integer> l = new Vector<>();
        for (int y = 2; y < Tetris.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (Tetris.MESH[y][x] == '0') {
                    is_full = false;
                    break;
                }
            }

            if (is_full)
                l.add(y);
        }
        Tetris.top -= l.size();

        if (l.isEmpty())
            return;

        //리스트에 저장된 라인들을 지움
        for (int i : l){
            highlightLine(i);
        }
        PauseTransition wait = new PauseTransition(Duration.millis(1000));
        wait.setOnFinished(event -> {
            for (int i : l) {
                for (int line = i; line > 2; line--) {
                    Tetris.MESH[line] = Tetris.MESH[line - 1];
                }
                Tetris.MESH[2] = new char[Tetris.WIDTH];
                Arrays.fill(Tetris.MESH[2], '0');

                Tetris.score += 50;
                Tetris.linesNo++;
                Tetris.changeSpeed();
            }
        });
        wait.play();
    }
     */

    public static void highlightLine(int line) {
        for (int x = 0; x < Tetris.WIDTH; x++) {
            Rectangle r = Tetris.rectMesh[line][x]; // rectMesh 배열에서 Rectangle 객체를 가져옴
            if (r != null) {
                r.setFill(Color.RED); // 색상을 빨간색으로 변경
            }
        }

        // 0.3초동안 빨간색 유지
        PauseTransition wait = new PauseTransition(Duration.millis(300));
        wait.play();
    }

    public static void highlightBlock(int x, int y) {
        Rectangle r = Tetris.rectMesh[y][x];
        if (r != null) {
            r.setFill(Color.RED);
        }

        // 0.3초동안 빨간색 유지
        PauseTransition wait = new PauseTransition(Duration.millis(300));
        wait.play();
    }


    private static void updateTop(TetrominoBase tb) {
        Tetris.top = 0;
        for (char[] line : Tetris.MESH) {
            for (char c : line) {
                if (c != '0')
                    return;
            }
            Tetris.top++;
        }
    }
}

