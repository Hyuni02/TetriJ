package com.snust.tetrij.Controller;

import com.snust.tetrij.GameManager;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.snust.tetrij.Controller.ScoreBoardController.currentScoreId;
import static com.snust.tetrij.Controller.ScoreBoardController.scoreData;
import static com.snust.tetrij.Controller.ScoreBoardController.highlightIndex;
import static com.snust.tetrij.GameScene.GameSceneMulti.MultiTetrisController.controller;
import static com.snust.tetrij.GameScene.GameSceneSingle.SingleTetrisController.controller_s;

public class ResolutionManager {

    static String curResolution;
    public static int curHeight;
    public static int curWidth;
    public static boolean isHighlight = true;

    public static void resolutionInitialize() {
        loadSettings();  //json파일 읽어옴
        System.out.println(curResolution);  //디버그용.. 한번 확인해보이소~
        String[] parts = curResolution.split("x");

        if (parts.length == 2) {
            curWidth = Integer.parseInt(parts[0]);
            curHeight = Integer.parseInt(parts[1]);

            System.out.println("Width: " + curWidth);
            System.out.println("Height: " + curHeight);
        }
    }

    public static void setStartMenuResolution(Parent root, int height, int width) {
        if (height == 600 && width == 900) {
            setStartMenu900x600(root);
        } else if (height == 800 && width == 1200) {
            setStartMenu1200x800(root);
        }
    }

    public static void setScoreBoardResolution(Parent root, int height, int width){
        if (height == 600 && width == 900) {
            setScoreBoard900x600(root);
        } else if (height == 800 && width == 1200) {
            setScoreBoard1200x800(root);
        }
    }
    public static void setSettingMenuResolution(Parent root, int height, int width) {
        if (height == 600 && width == 900) {
            setSettingMenu900x600(root);
        } else if (height == 800 && width == 1200) {
            setSettingMenu1200x800(root);
        }
    }

    public static void setKeySettingMenuResolution(Parent root, int height, int width) {
        if (height == 600 && width == 900) {
            setKeySetting900x600(root);
        } else if (height == 800 && width == 1200) {
            setKeySetting1200x800(root);
        }
    }

    /*
        시작 메뉴 해상도 설정
        */

    public static void setStartMenu900x600(Parent root) {
        setTextLayout(root, "#tetrijTitle", 270.0, 130.0, "-fx-font-size: 60pt;");

        double buttonLayoutX = 325.0;
        double buttonLayoutY = 190.0;
        double buttonOffset = 70.0;

        setButtonLayout(root, "#startBtn1", buttonLayoutX, buttonLayoutY + 0*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn2", buttonLayoutX, buttonLayoutY + 1*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn3", buttonLayoutX, buttonLayoutY + 2*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn4", buttonLayoutX, buttonLayoutY + 3*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");

        setTextLayout(root, "#upText", 480.0, 450.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#downText", 480.0, 480.0, "-fx-font-size: 12pt;");
    }

    public static void setStartMenu1200x800(Parent root) {
        setTextLayout(root, "#tetrijTitle", 350.0, 180.0, "-fx-font-size: 80pt;");

        double buttonLayoutX = 470.0;
        double buttonLayoutY = 260.0;
        double buttonOffset = 100.0;

        setButtonLayout(root, "#startBtn1", buttonLayoutX, buttonLayoutY + 0*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn2", buttonLayoutX, buttonLayoutY + 1*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn3", buttonLayoutX, buttonLayoutY + 2*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#startBtn4", buttonLayoutX, buttonLayoutY + 3*buttonOffset, 220.0, 60.0, "-fx-font-size: 15pt;");

        setTextLayout(root, "#upText", 650.0, 600.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#downText", 650.0, 630.0, "-fx-font-size: 15pt;");
    }

    /*
    스코어보드 해상도 설정
    */

    public static void setScoreBoard900x600(Parent root) {
        setButtonLayout(root, "#exitButton", 740.0, 500.0, 100.0, 30.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#scoreBoardTitle", 180, 80.0, "-fx-font-size: 46pt;");
        setTextLayout(root, "#diffText", 50.0, 50.0, "-fx-font-size: 15pt;");

        String cssLabel = "-fx-font-size: 26px;";
        String highlight = "-fx-text-fill: #ff8989; -fx-font-weight: bold;-fx-font-size: 26px;";

        double layoutX = 160.0;
        double layoutY = 100.0;
        double offset = 40.0;

        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset, cssLabel);
        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset, cssLabel);
        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset, cssLabel);
        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset, cssLabel);
        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset, cssLabel);
        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset, cssLabel);
        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset, cssLabel);
        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset, cssLabel);
        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset, cssLabel);
        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset, cssLabel);

        if(highlightIndex <= 10 && isHighlight) {
            setLabelLayout(root, "#score" + Integer.toString(highlightIndex), layoutX, layoutY + (highlightIndex - 1) * offset, highlight);
            isHighlight = false;
        }

        setComboBoxLayout(root, "#difficultyComboBox", 10.0, 50.0, 140.0, 40.0, "-fx-font-size: 12pt;");
    }

    public static void setScoreBoard1200x800(Parent root) {
        setButtonLayout(root, "#exitButton", 950.0, 650.0, 150.0, 50.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#scoreBoardTitle", 270.0, 80.0, "-fx-font-size: 60pt;");
        setTextLayout(root, "#diffText", 90.0, 75.0, "-fx-font-size: 18pt;");

        String cssLabel = "-fx-font-size: 32px;";
        String highlight = "-fx-text-fill: #ff8989; -fx-font-weight: bold;-fx-font-size: 32px;";


        double layoutX = 250;
        double layoutY = 150;
        double offset = 50;

        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset, cssLabel);
        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset, cssLabel);
        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset, cssLabel);
        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset, cssLabel);
        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset, cssLabel);
        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset, cssLabel);
        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset, cssLabel);
        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset, cssLabel);
        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset, cssLabel);
        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset, cssLabel);

        if(highlightIndex <= 10 && isHighlight) {
            setLabelLayout(root, "#score" + Integer.toString(highlightIndex), layoutX, layoutY + (highlightIndex - 1) * offset, highlight);
            isHighlight = false;
        }

        setComboBoxLayout(root, "#difficultyComboBox", 70.0, 90.0, 130.0, 50.0, "-fx-font-size: 15pt;-fx-font-family: \"KenVector Future\";");
    }

    /*
    환경설정 메뉴 해상도 설정
     */

    public static void setSettingMenu900x600(Parent root) {
        double buttonWidth = 280.0;
        double buttonHeight = 40.0;
        double buttonLayoutX =560.0;
        double buttonLayoutY = 340.0;
        double buttonOffset = 50;
        setTextLayout(root, "#settingTitle", 300.0, 67.0, "-fx-font-size: 42pt;");
        setButtonLayout(root, "#scoreBoardInitButton", buttonLayoutX, buttonLayoutY + 0*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 10pt;");
        setButtonLayout(root, "#defaultButton", buttonLayoutX, buttonLayoutY + 1*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 10pt;");
        setButtonLayout(root, "#saveButton", buttonLayoutX, buttonLayoutY + 2*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 10pt;");
        setButtonLayout(root, "#exitButton", buttonLayoutX, buttonLayoutY + 3*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 10pt;");

        double settingWidth = 240;
        double settingHeight = 40;
        setLabelLayout(root, "#text1", 50.0, 110.0, "-fx-font-size: 12pt;");
        setComboBoxLayout(root, "#sizeComboBox", 50.0, 140.0, settingWidth, settingHeight, "-fx-font-size: 12pt;");

        setLabelLayout(root, "#text2", 325.0, 110.0, "-fx-font-size: 12pt;");
        setButtonLayout(root, "#KeySettingButton", 325.0, 140.0, settingWidth, settingHeight, "-fx-font-size: 12pt;");

        setCheckBoxLayout(root, "#colorBlindModeCheckBox", 600.0, 140.0,settingWidth, settingHeight, "-fx-font-size: 12pt;");
    }
    public static void setSettingMenu1200x800(Parent root) {
        double buttonWidth = 310.0;
        double buttonHeight = 50.0;
        double buttonLayoutX =800.0;
        double buttonLayoutY = 420.0;
        double buttonOffset = 80;
        setTextLayout(root, "#settingTitle", 380.0, 100.0, "-fx-font-size: 62pt;");
        setButtonLayout(root, "#scoreBoardInitButton", buttonLayoutX, buttonLayoutY + 0*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 11pt;");
        setButtonLayout(root, "#defaultButton", buttonLayoutX, buttonLayoutY + 1*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 11pt;");
        setButtonLayout(root, "#saveButton", buttonLayoutX, buttonLayoutY + 2*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 11pt;");
        setButtonLayout(root, "#exitButton", buttonLayoutX, buttonLayoutY + 3*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 11pt;");

        double settingWidth = 310;
        double settingHeight = 50;
        setLabelLayout(root, "#text1", 70.0, 140.0, "-fx-font-size: 13pt;");
        setComboBoxLayout(root, "#sizeComboBox", 70.0, 170.0, settingWidth, settingHeight, "-fx-font-size: 13pt;");

        setLabelLayout(root, "#text2", 434.0, 140.0, "-fx-font-size: 12pt;");
        setButtonLayout(root, "#KeySettingButton", 434.0, 170.0, settingWidth, settingHeight, "-fx-font-size: 13pt;");

        setCheckBoxLayout(root, "#colorBlindModeCheckBox", 800.0, 170.0,settingWidth, settingHeight, "-fx-font-size: 13pt;");
    }

    /*
    키 설정 메뉴 해상도 설정
    */

    private static void setKeySetting900x600(Parent root) {
        final int X1 = 170;
        final int X2 = 470;
        final int labelY = 54;
        final int offset = 100;
        final int buttonY = 84;
        final String css = "-fx-font-size: 14pt;";
        final int buttonWidth = 200;
        final int buttonHeight =50;
        // 플레이어 1 레이아웃
        setLabelLayout(root, "#player1Label", X1, 10, "-fx-font-size: 18pt; -fx-text-fill: #ffefba; -fx-font-weight: bold;");
        setLabelLayout(root, "#leftMoveButton1", X1, labelY, css);
        setButtonLayout(root, "#leftMoveKeyButton", X1, buttonY, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rightMoveButton1", X1, labelY+offset, css);
        setButtonLayout(root, "#rightMoveKeyButton", X1, buttonY+offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rotateButton1", X1, labelY+2*offset, css);
        setButtonLayout(root, "#rotateMoveKeyButton", X1, buttonY+2*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#downMoveButton1", X1, labelY+3*offset, css);
        setButtonLayout(root, "#downMoveKeyButton", X1, buttonY+3*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#dropButton1", X1, labelY+4*offset, css);
        setButtonLayout(root, "#dropKeyButton", X1, buttonY+4*offset, buttonWidth, buttonHeight, css);

        // 플레이어 2 레이아웃
        setLabelLayout(root, "#player2Label", X2, 10, "-fx-font-size: 18pt; -fx-text-fill: #ffefba; -fx-font-weight: bold;");
        setLabelLayout(root, "#leftMoveButton2", X2, labelY, css);
        setButtonLayout(root, "#p2LeftButton", X2, buttonY, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rightMoveButton2", X2, labelY+offset, css);
        setButtonLayout(root, "#p2RightButton", X2, buttonY+offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rotateButton2", X2, labelY+2*offset, css);
        setButtonLayout(root, "#p2RotateButton", X2, buttonY+2*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#downMoveButton2", X2, labelY+3*offset, css);
        setButtonLayout(root, "#p2DownButton", X2, buttonY+3*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#dropButton2", X2, labelY+4*offset, css);
        setButtonLayout(root, "#p2DropButton", X2, buttonY+4*offset, buttonWidth, buttonHeight, css);

        setButtonLayout(root, "#backButton", 740, buttonY+4*offset+20, 100, 20, css);
    }


    private static void setKeySetting1200x800(Parent root) {
        final int X1 = 280;
        final int X2 = 630;
        final int labelY = 54;
        final int offset = 135;
        final int buttonY = 84;
        final String css = "-fx-font-size: 18pt;";
        final int buttonWidth = 250;
        final int buttonHeight =60;
        // 플레이어 1 레이아웃
        setLabelLayout(root, "#player1Label", X1, 10, "-fx-font-size: 22pt; -fx-text-fill: #ffefba; -fx-font-weight: bold;");
        setLabelLayout(root, "#leftMoveButton1", X1, labelY, css);
        setButtonLayout(root, "#leftMoveKeyButton", X1, buttonY, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rightMoveButton1", X1, labelY+offset, css);
        setButtonLayout(root, "#rightMoveKeyButton", X1, buttonY+offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rotateButton1", X1, labelY+2*offset, css);
        setButtonLayout(root, "#rotateMoveKeyButton", X1, buttonY+2*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#downMoveButton1", X1, labelY+3*offset, css);
        setButtonLayout(root, "#downMoveKeyButton", X1, buttonY+3*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#dropButton1", X1, labelY+4*offset, css);
        setButtonLayout(root, "#dropKeyButton", X1, buttonY+4*offset, buttonWidth, buttonHeight, css);

        // 플레이어 2 레이아웃
        setLabelLayout(root, "#player2Label", X2, 10, "-fx-font-size: 22pt; -fx-text-fill: #ffefba; -fx-font-weight: bold;");
        setLabelLayout(root, "#leftMoveButton2", X2, labelY, css);
        setButtonLayout(root, "#p2LeftButton", X2, buttonY, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rightMoveButton2", X2, labelY+offset, css);
        setButtonLayout(root, "#p2RightButton", X2, buttonY+offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#rotateButton2", X2, labelY+2*offset, css);
        setButtonLayout(root, "#p2RotateButton", X2, buttonY+2*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#downMoveButton2", X2, labelY+3*offset, css);
        setButtonLayout(root, "#p2DownButton", X2, buttonY+3*offset, buttonWidth, buttonHeight, css);
        setLabelLayout(root, "#dropButton2", X2, labelY+4*offset, css);
        setButtonLayout(root, "#p2DropButton", X2, buttonY+4*offset, buttonWidth, buttonHeight, css);

        setButtonLayout(root, "#backButton", 940, buttonY+4*offset+20, 150, 40, css);
    }



    /*
    레이아웃 설정 관련 함수
     */
    private static void setButtonLayout(Parent root, String id, double layoutX, double layoutY, double prefWidth, double prefHeight, String css) {
        Button button = (Button) root.lookup(id);
        if (button != null) {
            button.setLayoutX(layoutX);
            button.setLayoutY(layoutY);
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight);
            button.setStyle(css);
        }
    }

    private static void setTextLayout(Parent root, String id, double layoutX, double layoutY, String css) {
        Text text = (Text) root.lookup(id);
        if (text != null) {
            text.setLayoutX(layoutX);
            text.setLayoutY(layoutY);
            text.setStyle(css);
        }
    }
    private static void setLabelLayout(Parent root, String id, double layoutX, double layoutY){
        Label label = (Label) root.lookup(id);
        if (label != null) {
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
        }
    }
    private static void setLabelLayout(Parent root, String id, double layoutX, double layoutY, String css) {

        Label label = (Label) root.lookup(id);
        setLabelLayout(root, id, layoutX,layoutY);
        if (label != null) {

            label.setStyle(css);
        }
    }

    private static void setComboBoxLayout(Parent root, String id, double layoutX, double layoutY, double prefWidth, double prefHeight, String css) {
        ComboBox<?> comboBox = (ComboBox<?>) root.lookup(id);
        if (comboBox != null) {
            comboBox.setLayoutX(layoutX);
            comboBox.setLayoutY(layoutY);
            comboBox.setPrefWidth(prefWidth);
            comboBox.setPrefHeight(prefHeight);
            comboBox.setStyle(css);
        }
    }

    private static void setCheckBoxLayout(Parent root, String id, double layoutX, double layoutY, double prefWidth, double prefHeight, String css) {
        CheckBox checkBox = (CheckBox) root.lookup(id);
        if (checkBox != null) {
            checkBox.setLayoutX(layoutX);
            checkBox.setLayoutY(layoutY);
            checkBox.setPrefWidth(prefWidth);
            checkBox.setPrefHeight(prefHeight);
            checkBox.setStyle(css);
        }
    }

    private static void loadSettings() {    //셋팅 파일 읽어옴
        try {
            // 빌드 환경에서 셋팅 파일 읽어옴
            Path filePath = GameManager.Jsonsetting();

            // 파일 읽기
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // JSON 객체 생성
            JSONObject setting = new JSONObject(stringBuilder.toString());
            curResolution = setting.getString("screenSize");
            controller_s.color_weakness = setting.getBoolean("isColorBlind");
            controller.color_weakness = setting.getBoolean("isColorBlind");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No setting.json");
        }
    }

}
