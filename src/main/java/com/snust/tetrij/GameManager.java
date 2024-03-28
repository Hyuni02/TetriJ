package com.snust.tetrij;

public class GameManager extends Tetris {
    protected int _resolutionX = 800;
    protected int _resolutionY = 600;
    protected int _score = 0;

    protected void startGame() {
        // 게임 시작
        System.out.println("게임 시작");
        _score = 1000;
    }

    protected void openSetting() {
        // 환경설정 실행
        System.out.println("환경 설정");
    }

    protected void openScoreboard() {
        // 스코어보드 실행
        System.out.println("스코어보드");
    }
}
