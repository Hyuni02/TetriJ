package com.snust.tetrij;

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
    static int curHeight;
    static int curWidth;

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
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(400.0);
            imageView.setLayoutX(220.0);
            imageView.setLayoutY(50.0);
        }
        double buttonLayoutX = 310.0;
        double buttonLayoutY = 180.0;
        double buttonOffset = 70.0;

        setButtonLayout(root, "#startBtn1", buttonLayoutX, buttonLayoutY + 0*buttonOffset, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn2", buttonLayoutX, buttonLayoutY + 1*buttonOffset, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn3", buttonLayoutX, buttonLayoutY + 2*buttonOffset, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn4", buttonLayoutX, buttonLayoutY + 3*buttonOffset, 220.0, 60.0, "-fx-font-size: 20pt;");

        setTextLayout(root, "#upText", 500.0, 450.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#downText", 500.0, 480.0, "-fx-font-size: 12pt;");
    }

    public static void setStartMenu1200x800(Parent root) {
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(180.0);
            imageView.setFitWidth(480.0);
            imageView.setLayoutX(340.0);
            imageView.setLayoutY(50.0);
        }

        double buttonLayoutX = 470.0;
        double buttonLayoutY = 260.0;
        double buttonOffset = 100.0;

        setButtonLayout(root, "#startBtn1", buttonLayoutX, buttonLayoutY + 0*buttonOffset, 220.0, 60.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn2", buttonLayoutX, buttonLayoutY + 1*buttonOffset, 220.0, 60.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn3", buttonLayoutX, buttonLayoutY + 2*buttonOffset, 220.0, 60.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn4", buttonLayoutX, buttonLayoutY + 3*buttonOffset, 220.0, 60.0, "-fx-font-size: 22pt;");

        setTextLayout(root, "#upText", 650.0, 600.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#downText", 650.0, 630.0, "-fx-font-size: 15pt;");
    }

    /*
    스코어보드 해상도 설정
    */

    public static void setScoreBoard900x600(Parent root) {
        setButtonLayout(root, "#exitButton", 700.0, 450.0, 100.0, 30.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#scoreBoardTitle", 300.0, 54.0, "-fx-font-size: 40pt;");
        setTextLayout(root, "#diffText", 50.0, 50.0, "-fx-font-size: 15pt;");

        String cssLabel = "-fx-font-size: 18pt;";
        double layoutX = 170.0;
        double layoutY = 80.0;
        double offset = 30.0;

//        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset, cssLabel);
//        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset, cssLabel);
//        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset, cssLabel);
//        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset, cssLabel);
//        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset, cssLabel);
//        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset, cssLabel);
//        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset, cssLabel);
//        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset, cssLabel);
//        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset, cssLabel);
//        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset, cssLabel);
        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset);
        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset);
        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset);
        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset);
        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset);
        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset);
        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset);
        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset);
        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset);
        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset);

        setComboBoxLayout(root, "#difficultyComboBox", 30.0, 60.0, 100.0, 30.0, "-fx-font-size: 8pt;");
    }

    public static void setScoreBoard1200x800(Parent root) {
        setButtonLayout(root, "#exitButton", 900.0, 650.0, 180.0, 60.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#scoreBoardTitle", 440.0, 60.0, "-fx-font-size: 40pt;");
        setTextLayout(root, "#diffText", 90.0, 75.0, "-fx-font-size: 15pt;");

        String cssLabel = "-fx-font-size: 20pt;";
        String highlight = "-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-size: 20pt;";

        double layoutX = 280;
        double layoutY = 120;
        double offset = 40;

//        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset, cssLabel);
//        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset, cssLabel);
//        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset, cssLabel);
//        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset, cssLabel);
//        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset, cssLabel);
//        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset, cssLabel);
//        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset, cssLabel);
//        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset, cssLabel);
//        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset, cssLabel);
//        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset, cssLabel);
        setLabelLayout(root, "#score1", layoutX, layoutY + 0*offset);
        setLabelLayout(root, "#score2", layoutX, layoutY + 1*offset);
        setLabelLayout(root, "#score3", layoutX, layoutY + 2*offset);
        setLabelLayout(root, "#score4", layoutX, layoutY + 3*offset);
        setLabelLayout(root, "#score5", layoutX, layoutY + 4*offset);
        setLabelLayout(root, "#score6", layoutX, layoutY + 5*offset);
        setLabelLayout(root, "#score7", layoutX, layoutY + 6*offset);
        setLabelLayout(root, "#score8", layoutX, layoutY + 7*offset);
        setLabelLayout(root, "#score9", layoutX, layoutY + 8*offset);
        setLabelLayout(root, "#score10", layoutX, layoutY + 9*offset);

        setComboBoxLayout(root, "#difficultyComboBox", 70.0, 90.0, 100.0, 30.0, "-fx-font-size: 8pt;");
    }
    public static void setScoreBoard1200x800(Parent root, String name, int score) {
        setScoreBoard1200x800(root);

    }

    /*
    환경설정 메뉴 해상도 설정
     */

    public static void setSettingMenu900x600(Parent root) {
        double buttonWidth = 150.0;
        double buttonHeight = 30.0;
        double buttonLayoutX =700.0;
        double buttonLayoutY = 380.0;
        double buttonOffset = 50;
        setTextLayout(root, "#settingTitle", 320.0, 67.0, "-fx-font-size: 42pt;");
        setButtonLayout(root, "#scoreBoardInitButton", buttonLayoutX, buttonLayoutY + 0*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#defaultButton", buttonLayoutX, buttonLayoutY + 1*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#saveButton", buttonLayoutX, buttonLayoutY + 2*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#exitButton", buttonLayoutX, buttonLayoutY + 3*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");

        setLabelLayout(root, "#text1", 80.0, 90.0, "-fx-font-size: 12pt;");
        setComboBoxLayout(root, "#sizeComboBox", 80.0, 120.0, 150.0, 30.0, "-fx-font-size: 12pt;");

        setLabelLayout(root, "#text2", 350.0, 90.0, "-fx-font-size: 12pt;");
        setButtonLayout(root, "#KeySettingButton", 350, 120, buttonWidth, buttonHeight, "-fx-font-size: 12pt;");

        setCheckBoxLayout(root, "#colorBlindModeCheckBox", 650.0, 120.0, "-fx-font-size: 15pt;");
    }
    public static void setSettingMenu1200x800(Parent root) {
        double buttonWidth = 200.0;
        double buttonHeight = 50.0;
        double buttonLayoutX =900.0;
        double buttonLayoutY = 500.0;
        double buttonOffset = 70;
        setTextLayout(root, "#settingTitle", 480.0, 67.0, "-fx-font-size: 42pt;");
        setButtonLayout(root, "#scoreBoardInitButton", buttonLayoutX, buttonLayoutY + 0*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#defaultButton", buttonLayoutX, buttonLayoutY + 1*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#saveButton", buttonLayoutX, buttonLayoutY + 2*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#exitButton", buttonLayoutX, buttonLayoutY + 3*buttonOffset, buttonWidth, buttonHeight, "-fx-font-size: 8pt;");

        setLabelLayout(root, "#text1", 80.0, 150.0, "-fx-font-size: 12pt;");
        setComboBoxLayout(root, "#sizeComboBox", 80.0, 180.0, 150.0, 30.0, "-fx-font-size: 12pt;");

        setLabelLayout(root, "#text2", 450.0, 150.0, "-fx-font-size: 12pt;");
        setButtonLayout(root, "#KeySettingButton", 450, 180, 150, 40, "-fx-font-size: 12pt;");

        setCheckBoxLayout(root, "#colorBlindModeCheckBox", 850.0, 180.0, "-fx-font-size: 15pt;");
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
