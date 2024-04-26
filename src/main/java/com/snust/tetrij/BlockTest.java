package com.snust.tetrij;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockTest {
    @Test
    public void EasyBlockTest() {
        Map<Integer, Integer> blockCount = new HashMap<>();
        TetrisBoardController.field = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            blockCount.put(i, 0);
        }

        int totalSelections = 1000;
        for (int i = 0; i < totalSelections; i++) {
            TetrisBoardController.RWS(Tetris.difficulty.EASY);
        }
        for (int i : TetrisBoardController.field){
            int selectedBlock = i;
            blockCount.put(selectedBlock, blockCount.get(selectedBlock) + 1);
        }

        //확률 검증 ㄱㄱ
        double expectedIRate = 0.16; // 쉬움 난이도에서 I 블록의 예상 선택 비율
        double actualRate = blockCount.get(1) / (double) TetrisBoardController.field.size();
        System.out.println("EASY 모드: ");
        System.out.println("총 블록 수: " + TetrisBoardController.field.toArray().length);
        System.out.println("I 개수: " + blockCount.get(1));
        System.out.println("I 블록 비율: " + actualRate);

        // 오차범위 5% 이내인지 확인
        Assert.assertTrue(Math.abs(expectedIRate - actualRate) <= 0.05);
    }

    @Test
    public void HardBlockTest() {
        Map<Integer, Integer> blockCount = new HashMap<>();
        TetrisBoardController.field = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            blockCount.put(i, 0);
        }

        // 200번 이상 블록 선택 반복 -> 블록 14400개는 만들어짐 ㅎㅎ
        int totalSelections = 1000;
        for (int i = 0; i < totalSelections; i++) {
            TetrisBoardController.RWS(Tetris.difficulty.HARD);
        }
        for (int i : TetrisBoardController.field){
            int selectedBlock = i;
            blockCount.put(selectedBlock, blockCount.get(selectedBlock) + 1);
        }

        //확률 검증 ㄱㄱ
        double expectedIRate = 0.11; // 어려움 난이도에서 I 블록의 예상 선택 비율
        double actualRate = blockCount.get(1) / (double) TetrisBoardController.field.size();
        System.out.println("Hard 모드: ");
        System.out.println("총 블록 수: " + TetrisBoardController.field.toArray().length);
        System.out.println("I 개수: " + blockCount.get(1));
        System.out.println("I 블록 비율: " + actualRate);

        // 오차범위 5% 이내인지 확인
        Assert.assertTrue(Math.abs(expectedIRate - actualRate) <= 0.05);
    }

}
