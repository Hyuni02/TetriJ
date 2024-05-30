package com.snust.tetrij;

import com.snust.tetrij.GameScene.GameControllerBase;
import com.snust.tetrij.GameScene.GameSceneSingle.SingleBoardController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.snust.tetrij.GameScene.GameSceneSingle.SingleBoardController.boardController_s;

public class BlockTest {
    @Test
    public void EasyBlockTest() {
        Map<Integer, Integer> blockCount = new HashMap<>();
        ArrayList<Integer> field = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            blockCount.put(i, 0);
        }

        int totalSelections = 1000;
        for (int i = 0; i < totalSelections; i++) {
            field.add(boardController_s.RWS(GameControllerBase.difficulty.EASY));
        }
        for (int i : field){
            int selectedBlock = i;
            blockCount.put(selectedBlock, blockCount.get(selectedBlock) + 1);
        }

        //확률 검증 ㄱㄱ
        double expectedIRate = 0.16; // 쉬움 난이도에서 I 블록의 예상 선택 비율
        double actualRate = blockCount.get(1) / (double) field.size();
        System.out.println("EASY 모드: ");
        System.out.println("총 블록 수: " + field.toArray().length);
        System.out.println("I 개수: " + blockCount.get(1));
        System.out.println("I 블록 비율: " + actualRate);

        // 오차범위 5% 이내인지 확인
        Assertions.assertTrue(Math.abs(expectedIRate - actualRate) <= 0.05);
    }

    @Test
    public void HardBlockTest() {
        Map<Integer, Integer> blockCount = new HashMap<>();
        ArrayList<Integer> field = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            blockCount.put(i, 0);
        }

        // 200번 이상 블록 선택 반복 -> 블록 14400개는 만들어짐 ㅎㅎ
        int totalSelections = 1000;
        for (int i = 0; i < totalSelections; i++) {
            field.add(boardController_s.RWS(GameControllerBase.difficulty.HARD));
        }
        for (int i : field){
            int selectedBlock = i;
            blockCount.put(selectedBlock, blockCount.get(selectedBlock) + 1);
        }

        //확률 검증 ㄱㄱ
        double expectedIRate = 0.11; // 어려움 난이도에서 I 블록의 예상 선택 비율
        double actualRate = blockCount.get(1) / (double) field.size();
        System.out.println("Hard 모드: ");
        System.out.println("총 블록 수: " + field.toArray().length);
        System.out.println("I 개수: " + blockCount.get(1));
        System.out.println("I 블록 비율: " + actualRate);

        // 오차범위 5% 이내인지 확인
        Assertions.assertTrue(Math.abs(expectedIRate - actualRate) <= 0.05);
    }

}
