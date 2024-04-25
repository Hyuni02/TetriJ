package com.snust.tetrij;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SetResolution {

    String curResolution;

    public static void setStartMenuResolution(Parent root, int height, int width) {
        if (height == 600 && width == 800) {
            setStartMenu600x800(root);
        } else if (height == 800 && width == 1000) {
            setStartMenu800x1000(root);
        }
    }

    public static void setScoreBoardResolution(Parent root, int height, int width){
        if (height == 600 && width == 800) {
            setScoreBoard600x800(root);
        } else if (height == 800 && width == 1000) {
            setScoreBoard800x1000(root);
        }
    }
    public static void setSettingMenuResolution(Parent root, int height, int width) {
        if (height == 600 && width == 800) {
            setSettingMenu600x800(root);
        } else if (height == 800 && width == 1000) {
            setSettingMenu800x1000(root);
        }
    }

    /*
        시작 메뉴 해상도 설정
        */

    public static void setStartMenu600x800(Parent root) {
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(400.0);
            imageView.setLayoutX(200.0);
            imageView.setLayoutY(50.0);
        }

        setButtonLayout(root, "#startBtn1", 270.0, 180.0, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn2", 270.0, 250.0, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn3", 270.0, 320.0, 220.0, 60.0, "-fx-font-size: 20pt;");
        setButtonLayout(root, "#startBtn4", 270.0, 390.0, 220.0, 60.0, "-fx-font-size: 20pt;");

        setTextLayout(root, "#upText", 400.0, 450.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#downText", 400.0, 480.0, "-fx-font-size: 12pt;");
    }

    public static void setStartMenu800x1000(Parent root) {
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(400.0);
            imageView.setLayoutX(200.0);
            imageView.setLayoutY(50.0);
        }

        setButtonLayout(root, "#startBtn1", 350.0, 230.0, 250.0, 90.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn2", 350.0, 330.0, 250.0, 90.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn3", 350.0, 430.0, 250.0, 90.0, "-fx-font-size: 22pt;");
        setButtonLayout(root, "#startBtn4", 350.0, 530.0, 250.0, 90.0, "-fx-font-size: 22pt;");

        setTextLayout(root, "#upText", 550.0, 600.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#downText", 550.0, 630.0, "-fx-font-size: 15pt;");
    }

    /*
    스코어보드 해상도 설정
    */

    public static void setScoreBoard600x800(Parent root) {
        setButtonLayout(root, "#exitButton", 600.0, 450.0, 100.0, 30.0, "-fx-font-size: 12pt;");
        setTextLayout(root, "#scoreBoardTitle", 240.0, 54.0, "-fx-font-size: 40pt;");
        setTextLayout(root, "#diffText", 50.0, 50.0, "-fx-font-size: 15pt;");

        String cssLabel = "-fx-font-size: 18pt;";
        setLabelLayout(root, "#score1", 170.0, 80.0, cssLabel);
        setLabelLayout(root, "#score2", 170.0, 110.0, cssLabel);
        setLabelLayout(root, "#score3", 170.0, 140.0, cssLabel);
        setLabelLayout(root, "#score4", 170.0, 170.0, cssLabel);
        setLabelLayout(root, "#score5", 170.0, 200.0, cssLabel);
        setLabelLayout(root, "#score6", 170.0, 230.0, cssLabel);
        setLabelLayout(root, "#score7", 170.0, 260.0, cssLabel);
        setLabelLayout(root, "#score8", 170.0, 290.0, cssLabel);
        setLabelLayout(root, "#score9", 170.0, 320.0, cssLabel);
        setLabelLayout(root, "#score10", 170.0, 350.0, cssLabel);

        setComboBoxLayout(root, "#difficultyComboBox", 30.0, 60.0, 100.0, 30.0, "-fx-font-size: 8pt;");
    }

    public static void setScoreBoard800x1000(Parent root) {
        setButtonLayout(root, "#exitButton", 600.0, 400.0, 150.0, 45.0, "-fx-font-size: 15pt;");
        setTextLayout(root, "#scoreBoardTitle", 300.0, 100.0, "-fx-font-size: 45pt;");

        String cssLabel = "-fx-font-size: 18pt;";
        setLabelLayout(root, "#score1", 170.0, 80.0, cssLabel);
        setLabelLayout(root, "#score2", 170.0, 110.0, cssLabel);
        setLabelLayout(root, "#score3", 170.0, 140.0, cssLabel);
        setLabelLayout(root, "#score4", 170.0, 170.0, cssLabel);
        setLabelLayout(root, "#score5", 170.0, 200.0, cssLabel);
        setLabelLayout(root, "#score6", 170.0, 230.0, cssLabel);
        setLabelLayout(root, "#score7", 170.0, 260.0, cssLabel);
        setLabelLayout(root, "#score8", 170.0, 290.0, cssLabel);
        setLabelLayout(root, "#score9", 170.0, 320.0, cssLabel);
        setLabelLayout(root, "#score10", 170.0, 350.0, cssLabel);

        setComboBoxLayout(root, "#difficultyComboBox", 60.0, 100.0, 120.0, 30.0, "-fx-font-size: 8pt;");
        setTextLayout(root, "#diffText",100.0, 100.0, "-fx-font-size: 10pt;");
    }

    /*
    환경설정 메뉴 해상도 설정
     */

    public static void setSettingMenu600x800(Parent root) {
        setTextLayout(root, "#settingTitle", 280.0, 67.0, "-fx-font-size: 42pt;");
        setButtonLayout(root, "#scoreBoardInitButton", 550.0, 380.0, 150.0, 30.0, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#defaultButton", 550.0, 430.0, 150.0, 30.0, "-fx-font-size: 8pt;");
        setButtonLayout(root, "#exitButton", 550.0, 480.0, 150.0, 30.0, "-fx-font-size: 8pt;");
        setCheckBoxLayout(root, "#colorBlindModeCheckBox", 438.0, 122.0, "-fx-font-size: 15pt;");
        setComboBoxLayout(root, "#sizeComboBox", 39.0, 107.0, 100.0, 30.0, "-fx-font-size: 12pt;");
    }
    public static void setSettingMenu800x1000(Parent root) {
        setTextLayout(root, "#settingTitle", 316.0, 117.0, "-fx-font-size: 45pt;");
        setButtonLayout(root, "#exitButton", 475.0, 336.0, 150.0, 45.0, "-fx-font-size: 15pt;");
        setButtonLayout(root, "#scoreBoardInitButton", 428.0, 248.0, 180.0, 40.0, "-fx-font-size: 15pt;");
        setComboBoxLayout(root, "#sizeComboBox", 39.0, 107.0, 120.0, 40.0, "-fx-font-size: 15pt;");
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

    private static void setLabelLayout(Parent root, String id, double layoutX, double layoutY, String css) {
        Label label = (Label) root.lookup(id);
        if (label != null) {
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
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
}
