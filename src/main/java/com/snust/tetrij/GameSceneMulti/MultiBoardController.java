package com.snust.tetrij.GameSceneMulti;

import com.snust.tetrij.Tetris;
import javafx.application.Platform;
import javafx.concurrent.Task;
import com.snust.tetrij.tetromino.*;

import static com.snust.tetrij.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameSceneMulti.MultiTetrisModel.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MultiBoardController {
    public final static MultiBoardController boardController = new MultiBoardController();

    private MultiBoardController() { }

    public int RWS(Tetris.difficulty dif){
        double[] fitnesses = null;
        switch (controller.currentDifficulty) {
            case EASY -> {
                fitnesses = new double[] {1,1,1,1.2,1,1,1};  //easy난이도에서는 L블럭 20퍼센트 더 많이
            }
            case HARD -> {
                fitnesses = new double[] {1,1,1,0.8,1,1,1};
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

    public void generateTetromino(int player) {
        TetrominoBase t = new TetrominoBase(false);
        int idx = RWS(Tetris.cur_dif);
        if (Tetris.cur_dif != Tetris.difficulty.ITEM) {
            switch(idx) {
                case 0 -> t = new Z(false);
                case 1 -> t = new I(false);
                case 2 -> t = new J(false);
                case 3 -> t = new L(false);
                case 4 -> t = new O(false);
                case 5 -> t = new S(false);
                case 6 -> t = new T(false);
            }
        }
        else {
            if (Tetris.deleted_lines <= 2) {
                Tetris.deleted_lines = 0;
                switch(idx) {
                    case 0 -> t = new Z(true);
                    case 1 -> t = new I(true);
                    case 2 -> t = new J(true);
                    case 3 -> t = new L(true);
                    case 4 -> t = new O(true);
                    case 5 -> t = new S(true);
                    case 6 -> t = new Weight();
                }
            }
            else {
                switch(idx) {
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
        model.bags[player].add(t);

        int start_pos_y = 0;
        for (int[] y: t.mesh) {
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
    }

    public void softDrop(TetrominoBase tb, int player) {
        eraseMesh(tb, player);
        tb.pos[0]++;
        if (!canMoveDown(tb, 1, player)) {
            updateTop(tb, player);
            tb.update_mesh(player);
            eraseLine(player);
            model.bags[player].remove(0);
            model.bags[player].remove(0);
            generateTetromino(player);
            return;
        }
        tb.update_mesh(player);

    }

    /**
     * 키다운 드롭 - 한번에 끝까지 내려가기
     * @param tb : 현재 조종중인 블록
     */
    public void hardDrop(TetrominoBase tb, int player) {
        eraseMesh(tb, player);
        int dropHeight = 0;
        while (canMoveDown(tb, dropHeight + 1, player)) {
            dropHeight++;
        }

        tb.pos[0] += dropHeight;
        tb.update_mesh(player);
        updateTop(tb, player);
        eraseLine(player);
        model.bags[player].remove(0);
        generateTetromino(player);
    }

    public void moveRightOnKeyPress(TetrominoBase tb, int player) {
        eraseMesh(tb, player);
        if (canMoveSideWays(tb, 1, player) && tb.can_move) {
            tb.pos[1]++;
        }
        tb.update_mesh(player);
    }

    public void moveLeftOnKeyPress(TetrominoBase tb, int player) {
        eraseMesh(tb, player);
        if (canMoveSideWays(tb, -1, player) && tb.can_move) {
            tb.pos[1]--;
        }
        tb.update_mesh(player);
    }

    public void rotateClockWise(TetrominoBase tb,int player) {
        eraseMesh(tb, player);
        int[][] rotated = canRotateClockwise(tb, player);
        if (rotated != null) {
            tb.mesh = rotated;
        }
        tb.update_mesh(player);
    }

    public boolean canMoveDown(TetrominoBase tb, int distance, int player) {
        // 블록의 아래쪽에 다른 블록이 있는지 확인
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tb.mesh[y][x] == 0) {
                    continue;
                }
                //바닥에 닿음
                if (y + tb.pos[0] + distance >= Tetris.HEIGHT) {
                    return false;
                }
                // 다른 블록과 충돌 - 무게추와 일반 블록 구분 필요
                if (model.MESH[player][y + tb.pos[0] + distance][x + tb.pos[1]] != '0') {
                    if (tb.name == 'w'){
                        tb.can_move = false;
                        return true;
                    }
                    else
                        return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveSideWays(TetrominoBase tb, int distance, int player) {
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
                if (model.MESH[player][tb.pos[0] + y][tb.pos[1] + x + distance] != '0') {
                    return false;
                }
            }
        }
        return true; // 아래로 이동 가능
    }

    public int[][] canRotateClockwise(TetrominoBase tb,int player) {
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
                    if (model.MESH[player][y+tb.pos[0]][x+tb.pos[1]] == 1) {
                        return null;
                    }
                }
            }
        }
        return rotatedShape;
    }

    public void eraseMesh(TetrominoBase tb, int player) {
        for (int y = tb.pos[0]; y < tb.pos[0] + 4; y++) {
            for (int x = tb.pos[1]; x < tb.pos[1] + 4; x++) {
                if (y >= Tetris.HEIGHT || y < 0)
                    continue;
                if (x >= Tetris.WIDTH || x < 0)
                    continue;

                if (tb.mesh[y-tb.pos[0]][x-tb.pos[1]] != 0)
                    model.MESH[player][y][x] = '0';
            }
        }
    }

    public void eraseLine(int player) {
        //리스트에 가득 찬 라인을 저장
        List<Integer> l = new Vector<>();
        for (int y = 2; y < Tetris.HEIGHT; y++) {
            boolean is_full = true;
            for (int x = 0; x < Tetris.WIDTH; x++) {
                if (model.MESH[player][y][x] == 'L') {
                    is_full = true;
                    break;
                }
                if (model.MESH[player][y][x] == '0') {
                    is_full = false;
                }
            }
            if (is_full)
                l.add(y);
        }

        if (l.isEmpty())
            return;

        int erased_lines_count = l.toArray().length;

        //리스트에 저장된 라인들을 지움
        Task<Void> eraseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int line : l) {
                    Platform.runLater(() -> {
                        // highlightLine(line, player); //삭제되는 블록색 바꾸기
                    });
                    Platform.runLater(() -> {
                        // 라인 지우기
                        for (int l = line; l > 2; l--) {
                            model.MESH[player][l] = model.MESH[player][l-1];  //블록 당기기
                        }
                        model.MESH[player][2] = new char[Tetris.WIDTH];
                        Arrays.fill(model.MESH[player][2], '0');

                    });
                }
                return null;
            }
        };
        Thread eraseThread = new Thread(eraseTask);
        eraseThread.setDaemon(true);
        eraseThread.start();
    }

/*    public static void highlightLine(int line, int player){
        for (int x = 0; x < Tetris.WIDTH; x++) {
            Rectangle r = Tetris.rectMesh[line][x]; // rectMesh 배열에서 Rectangle 객체를 가져옴
            if (r != null) {
                r.setFill(Color.RED); // 색상을 빨간색으로 변경
            }
        }

        // 0.3초동안 빨간색 유지
        PauseTransition wait = new PauseTransition(Duration.millis(300));
        wait.play();
    }*/



    private void updateTop(TetrominoBase tb, int player) {
        Tetris.top = Math.max(Tetris.HEIGHT - tb.pos[0], Tetris.top);
    }
}
