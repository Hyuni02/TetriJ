package com.snust.tetrij.GameScene.GameSceneSingle;

import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.tetromino.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisModel.model_s;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisView.view_s;

public class SingleBoardController {
    public final static SingleBoardController boardController_s = new SingleBoardController();

    public static Thread eraseThread;

    public SingleBoardController() {
    }

    public static int RWS(GameControllerBase.difficulty dif) {
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
        int idx = RWS(controller_s.currentDifficulty);
        if (controller_s.currentDifficulty != GameControllerBase.difficulty.ITEM) {
            switch (idx) {
                case 0 -> t = new Z(false);
                case 1 -> t = new I(false);
                case 2 -> t = new J(false);
                case 3 -> t = new L(false);
                case 4 -> t = new O(false);
                case 5 -> t = new S(false);
                case 6 -> t = new T(false);
//                default -> t = new I(false);
            }
        } else {
            if (controller_s.deleted_lines <= 4) {
                controller_s.deleted_lines = 0;
                switch (idx) {
                    case 0 -> t = new Z(true);
                    case 1 -> t = new I(true);
                    case 2 -> t = new J(true);
                    case 3 -> t = new L(true);
                    case 4 -> t = new O(true);
                    case 5 -> t = new S(true);
                    case 6 -> t = new T(true);
                    case 7 -> t = new Boom();
                    case 8 -> t = new Goo();
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

        t.pos[1] = 3;
        model_s.bag.add(t);

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
//        System.out.println("gen : " + t.name);
    }


    public static void softDrop(TetrominoBase tb) {
        eraseMesh(tb);
        tb.pos[0]++;
        if (!canMoveDown(tb, 1)) {
            updateTop(tb);
            tb.update_mesh(-1);
            if (tb.name == 'g') gooExplosion(tb);
            eraseLine();
            if (tb.name == 'b') explosion(tb);
            if (tb.name == 'V') verticalExplosion(tb);
            if (tb.name == 'B') bigExplosion(tb);

            model_s.bag.remove(0);
            generateTetromino();
            return;
        }
        tb.update_mesh(-1);
    }

    //Boom이 바닥에 닿았을 때 4x4를 지우는 함수
    public static void explosion(TetrominoBase tb) {
        int left = tb.pos[1];
        int top = tb.pos[0];
        for (int y = top; y < top + 4; y++) {
            if (y > view_s.HEIGHT - 1 || y < 0) {
                continue;
            }
            for (int x = left; x < left + 4; x++) {
                if (x < 0 || x > view_s.WIDTH - 1) {
                    continue;
                }
                model_s.MESH[y][x] = '0';
            }
        }
        Task<Void> eraseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("make explosion thread");
                for (int y = top; y < top + 4; y++) {
                    if (y > view_s.HEIGHT - 1 || y < 0) {
                        continue;
                    }
                    for (int x = left; x < left + 4; x++) {
                        if (x < 0 || x > view_s.WIDTH - 1) {
                            continue;
                        }
                        int finalX = x;
                        int finalY = y;
                        Platform.runLater(() -> {
                            highlightBlock(finalX, finalY); //삭제되는 블록색 바꾸기
                        });
                    }
                }
                killEraseThread();
                return null;
            }
        };
        eraseThread = new Thread(eraseTask);
        eraseThread.start();
    }

    public static void bigExplosion(TetrominoBase tb) {
        List<Integer> l = new Vector<>();
        for (int y = 0; y < view_s.HEIGHT; y++) {
            for (int x = 0; x < view_s.WIDTH; x++) {
                if (model_s.MESH[y][x] != '0') {
                    l.add(y);
                }
            }

            Platform.runLater(() -> {
                for (int line : l) {
                    // 라인 지우기
                    for (int j = line; j > 2; j--) {
                        model_s.MESH[j] = model_s.MESH[j - 1];  //블록 당기기
                    }
                    model_s.MESH[2] = new char[view_s.WIDTH];
                    Arrays.fill(model_s.MESH[2], '0');
                }
            });
//            Task<Void> eraseTask = new Task<Void>() {
//                @Override
//                protected Void call() throws Exception {
//                    for (int line : l) {
//                        Platform.runLater(() -> {
//                            highlightLine(line); //삭제되는 블록색 바꾸기
//                        });
//                        Platform.runLater(() -> {
//                            // 라인 지우기
//                            for (int l = line; l > 2; l--) {
//                                model_s.MESH[l] = model_s.MESH[l - 1];  //블록 당기기
//                            }
//                            model_s.MESH[2] = new char[view_s.WIDTH];
//                            Arrays.fill(model_s.MESH[2], '0');
//                        });
//                    }
//                    return null;
//                }
//            };
//            Thread eraseThread = new Thread(eraseTask);
//            eraseThread.setDaemon(true);
//            eraseThread.start();
        }
    }

    public static void verticalExplosion(TetrominoBase tb) {
        int left = tb.pos[1] + 1;
        int right = tb.pos[1] + 2;
        for (int y = 0; y < view_s.HEIGHT; y++) {
            model_s.MESH[y][left] = '0';
            model_s.MESH[y][right] = '0';
        }
        Task<Void> eraseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("make vertical erase thread");
                for (int y = 1; y < view_s.HEIGHT; y++) {
                    int finalY = y;
                    Platform.runLater(() -> {
                        highlightBlock(left, finalY); //삭제되는 블록색 바꾸기
                        highlightBlock(right, finalY);
                    });
                }
                killEraseThread();
                return null;
            }
        };
        eraseThread = new Thread(eraseTask);
        eraseThread.start();
    }

    public static void weightHardDrop(TetrominoBase tb) {
        int left = tb.pos[1];
        int right = tb.pos[1] + 4;
        int end = tb.pos[0] + 2;
        for (int y = 0; y < end; y++) {
            for (int x = left; x < right; x++) {
                if (model_s.MESH[y][x] != 'w') {
                    model_s.MESH[y][x] = '0';
                }
            }
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
        if (tb.name == 'g') gooExplosion(tb);
        eraseLine();
        if (tb.name == 'b') explosion(tb);
        if (tb.name == 'V') verticalExplosion(tb);
        if (tb.name == 'B') bigExplosion(tb);
        if (tb.name == 'w') weightHardDrop(tb);
        model_s.bag.remove(0);
        generateTetromino();
    }

    public static void gooExplosion(TetrominoBase tb){
        int left = tb.pos[1];
        int top = tb.pos[0];
        for (int y = top; y < top + 4; y++) {
            if (y > view_s.HEIGHT - 1 || y < 0) {
                continue;
            }
            for (int x = left; x < left + 4; x++) {
                if (x < 0 || x > view_s.WIDTH - 1) {
                    continue;
                }
                if(model_s.MESH[y][x] == '0') {
                    model_s.MESH[y][x] = 'g';
                }

                //리스트에 저장된 블록들을 지움
//                int finalX = x;
//                int finalY = y;
//                Task<Void> eraseTask = new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        Platform.runLater(() -> {
//                            highlightBlock(finalX, finalY); //삭제되는 블록색 바꾸기
//                        });
//                        return null;
//                    }
//                };
//                Thread eraseThread = new Thread(eraseTask);
//                eraseThread.setDaemon(true);
//                eraseThread.start();
            }
        }
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
        if (tb.name == 'w') {
            return;
        }
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
                //바닥에 닿음
                if (y + tb.pos[0] + distance >= view_s.HEIGHT) {
                    return false;
                }
                // 다른 블록과 충돌 - 무게추와 일반 블록 구분 필요
                if (model_s.MESH[y + tb.pos[0] + distance][x + tb.pos[1]] != '0') {
                    if (tb.name == 'w') {
                        tb.can_move = false;
                        continue;
                    } else
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
                if (x + tb.pos[1] + distance < 0 || x + tb.pos[1] + distance >= view_s.WIDTH) {
                    return false;
                }
                // Tetromino의 블록이 측면으로 이동할 때 충돌 여부 확인
                if (model_s.MESH[tb.pos[0] + y][tb.pos[1] + x + distance] != '0') {
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
                    if (y >= view_s.HEIGHT || x >= view_s.WIDTH
                        || y < 0 || x < 0) {
                        return null;
                    }
                    // 회전 후의 위치에 이미 다른 블록이 있는 경우
                    if (model_s.MESH[y + tb.pos[0]][x + tb.pos[1]] != '0') {
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
                if (y >= view_s.HEIGHT || y < 0)
                    continue;
                if (x >= view_s.WIDTH || x < 0)
                    continue;

                if (tb.mesh[y - tb.pos[0]][x - tb.pos[1]] != 0)
                    model_s.MESH[y][x] = '0';
            }
        }
    }

    public static void eraseLine() {
        //리스트에 가득 찬 라인을 저장
        List<Integer> l = new Vector<>();
        for (int y = 2; y < view_s.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < view_s.WIDTH; x++) {
                if (model_s.MESH[y][x] == 'L') {
                    is_full = true;
                    break;
                }
                if (model_s.MESH[y][x] == '0') {
                    is_full = false;
                }
            }
            if (is_full)
                l.add(y);
        }
        controller_s.deleted_lines += l.size();

        if (l.isEmpty())
            return;

//        Platform.runLater(() -> {
//            for (int line : l) {
//                // 라인 지우기
//                for (int j = line; j > 2; j--) {
//                    model_s.MESH[j] = model_s.MESH[j - 1];  //블록 당기기
//                }
//                model_s.MESH[2] = new char[view_s.WIDTH];
//                Arrays.fill(model_s.MESH[2], '0');
//                controller_s.score += 50;
//                controller_s.linesNo++;
//            }
//        });

        //리스트에 저장된 라인들을 지움
        Task<Void> eraseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("make line erase thread");
                for (int line : l) {
                    Platform.runLater(() -> {
                        highlightLine(line); //삭제되는 블록색 바꾸기
                    });
                    Platform.runLater(() -> {
                        // 라인 지우기
                        for (int l = line; l > 2; l--) {
                            model_s.MESH[l] = model_s.MESH[l - 1];  //블록 당기기
                        }
                        model_s.MESH[2] = new char[view_s.WIDTH];
                        Arrays.fill(model_s.MESH[2], '0');
                        controller_s.score += 50;
                        controller_s.linesNo++;
                    });
                }
                killEraseThread();
                return null;
            }
        };
        eraseThread = new Thread(eraseTask);
        eraseThread.start();
    }

    public static void killEraseThread(){
        System.out.println("kill erase thread");
        try{
            eraseThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        for (int x = 0; x < view_s.WIDTH; x++) {
            Rectangle r = (Rectangle) view_s.rect[line][x].getChildren().get(0); // rectMesh 배열에서 Rectangle 객체를 가져옴
            if (r != null) {
                r.setFill(Color.RED); // 색상을 빨간색으로 변경
            }
        }
        System.out.println("highlight");
        // 0.3초동안 빨간색 유지
        PauseTransition wait = new PauseTransition(Duration.millis(300));
        wait.play();
    }

    public static void highlightBlock(int x, int y) {
        System.out.println("highlight block");
        Rectangle r = (Rectangle) view_s.rect[y][x].getChildren().get(0);
        if (r != null) {
            r.setFill(Color.RED);
        }

        // 0.3초동안 빨간색 유지
        PauseTransition wait = new PauseTransition(Duration.millis(300));
        wait.play();
    }


    private static void updateTop(TetrominoBase tb) {
        boolean fin = false;
        controller_s.top = 20;
        for (char[] line : model_s.MESH) {
            if (fin) {
                break;
            }
            for (char c : line) {
                if (c != '0') {
                    fin = true;
                    break;
                }
            }
            controller_s.top--;
        }
        System.out.println(controller_s.top);
    }
}

