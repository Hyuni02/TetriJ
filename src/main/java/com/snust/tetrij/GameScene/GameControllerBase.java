package com.snust.tetrij.GameScene;


import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;

public class GameControllerBase {
    public static volatile boolean isPaused;
    public boolean isGameOver;
    public enum difficulty {EASY, NORMAL, HARD, ITEM, TIME};
    public volatile difficulty currentDifficulty;
    public int score;
    public int linesNo; //총 지운 줄 갯수
    public int top;
    public int deleted_lines;

    public boolean color_weakness;
    public boolean onPauseButton;

    public String screenSize;

    public GameControllerBase() {
        initController();
    }

    public void initController(){
        onPauseButton = false;
        isPaused = false;
        isGameOver = false;
        score = 0;
        linesNo = 0;
        top = 0;
        deleted_lines = 0;
        isGameOver = false;
    }

    public void loadSettings() {    //셋팅 파일 읽어옴
        try {
            File file = new File("src/main/resources/com/snust/tetrij/setting.json");
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            fileReader.close();

            JSONObject setting = new JSONObject(stringBuilder.toString());
            screenSize = setting.getString("screenSize");
            color_weakness = setting.getBoolean("isColorBlind");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
