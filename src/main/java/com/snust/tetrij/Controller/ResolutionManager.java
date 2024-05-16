package com.snust.tetrij.Controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.*;

public class ResolutionManager {

    static String curResolution;
    public static int curHeight;
    public static int curWidth;

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

        setComboBoxLayout(root, "#difficultyComboBox", 10.0, 50.0, 140.0, 40.0, "-fx-font-size: 12pt;");
    }

    public static void setScoreBoard1200x800(Parent root) {
        setButtonLayout(root, "#exitButton", 950.0, 650.0, 150.0, 50.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#scoreBoardTitle", 270.0, 80.0, "-fx-font-size: 60pt;");
        setTextLayout(root, "#diffText", 90.0, 75.0, "-fx-font-size: 18pt;");

        String cssLabel = "-fx-font-size: 32px;";

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
    private static void setCheckBoxLayout(Parent root, String id, double layoutX, double layoutY, String css) {
        CheckBox checkBox = (CheckBox) root.lookup(id);
        if (checkBox != null) {
            checkBox.setLayoutX(layoutX);
            checkBox.setLayoutY(layoutY);
            checkBox.setStyle(css);
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

    private static void getCurrentScoreTxt() {

    }

    private static void loadSettings() {    //셋팅 파일 읽어옴
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
            curResolution = setting.getString("screenSize");

        } catch (Exception e) {
//            e.printStackTrace();
            loadSettings_build();
        }
    }

    private static void loadSettings_build() {
        try {
            // 클래스 로더를 사용하여 리소스 파일 읽기
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/snust/tetrij/setting.json");
            if (inputStream == null) {
                System.err.println("설정 파일을 찾을 수 없습니다.");
                return;
            }

            // 입력 스트림을 문자열로 변환
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            // JSON 객체 생성
            JSONObject setting = new JSONObject(stringBuilder.toString());
            curResolution = setting.getString("screenSize");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No setting.json");
        }
    }
}
