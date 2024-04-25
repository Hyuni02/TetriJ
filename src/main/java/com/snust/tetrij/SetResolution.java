package com.snust.tetrij;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SetResolution {

    public static void setResolution(Parent root, int height, int width) {
        if (height == 600 && width == 800) {
            setStartMenu600x800(root);
            setScoreBoard600x800(root);
        } else if (height == 800 && width == 1000) {
            setStartMenu800x1000(root);
            setScoreBoard800x1000(root);
        }
    }

    public static void setStartMenu600x800(Parent root) {
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(400.0);
            imageView.setLayoutX(200.0);
            imageView.setLayoutY(50.0);
        }

        setButtonLayout(root, "#startBtn1", 270.0, 180.0, 220.0, 60.0);
        setButtonLayout(root, "#startBtn2", 270.0, 250.0, 220.0, 60.0);
        setButtonLayout(root, "#startBtn3", 270.0, 320.0, 220.0, 60.0);
        setButtonLayout(root, "#startBtn4", 270.0, 390.0, 220.0, 60.0);

        setTextLayout(root, "#upText", 550.0, 600.0);
        setTextLayout(root, "#downText", 550.0, 630.0);
    }

    public static void setStartMenu800x1000(Parent root) {
        ImageView imageView = (ImageView) root.lookup("#Tetrij");
        if (imageView != null) {
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(400.0);
            imageView.setLayoutX(200.0);
            imageView.setLayoutY(50.0);
        }

        setButtonLayout(root, "#startBtn1", 350.0, 230.0, 250.0, 90.0);
        setButtonLayout(root, "#startBtn2", 350.0, 330.0, 250.0, 90.0);
        setButtonLayout(root, "#startBtn3", 350.0, 430.0, 250.0, 90.0);
        setButtonLayout(root, "#startBtn4", 350.0, 530.0, 250.0, 90.0);

        setTextLayout(root, "#upText", 550.0, 600.0);
        setTextLayout(root, "#downText", 550.0, 630.0);
    }

    public static void setScoreBoard600x800(Parent root) {
        setButtonLayout(root, "#exitButton", 507.0, 341.0, 100.0, 30.0);
        setTextLayout(root, "#scoreBoardTitle", 200.0, 54.0);

        setLabelLayout(root, "#score1", 170.0, 80.0);
        setLabelLayout(root, "#score2", 170.0, 110.0);
        setLabelLayout(root, "#score3", 170.0, 140.0);
        setLabelLayout(root, "#score4", 170.0, 170.0);
        setLabelLayout(root, "#score5", 170.0, 200.0);
        setLabelLayout(root, "#score6", 170.0, 230.0);
        setLabelLayout(root, "#score7", 170.0, 260.0);
        setLabelLayout(root, "#score8", 170.0, 290.0);
        setLabelLayout(root, "#score9", 170.0, 320.0);
        setLabelLayout(root, "#score10", 170.0, 350.0);

        setComboBoxLayout(root, "#difficultyComboBox", 48.0, 50.0, 91.0, 22.0);
    }

    public static void setScoreBoard800x1000(Parent root) {
        setButtonLayout(root, "#exitButton", 600.0, 400.0, 150.0, 45.0);
        setTextLayout(root, "#scoreBoardTitle", 300.0, 100.0);

        setLabelLayout(root, "#score1", 250.0, 120.0);
        setLabelLayout(root, "#score2", 250.0, 160.0);
        setLabelLayout(root, "#score3", 250.0, 200.0);
        setLabelLayout(root, "#score4", 250.0, 240.0);
        setLabelLayout(root, "#score5", 250.0, 280.0);
        setLabelLayout(root, "#score6", 250.0, 320.0);
        setLabelLayout(root, "#score7", 250.0, 360.0);
        setLabelLayout(root, "#score8", 250.0, 400.0);
        setLabelLayout(root, "#score9", 250.0, 440.0);
        setLabelLayout(root, "#score10", 250.0, 480.0);

        setComboBoxLayout(root, "#difficultyComboBox", 60.0, 100.0, 120.0, 30.0);
    }

    private static void setButtonLayout(Parent root, String id, double layoutX, double layoutY, double prefWidth, double prefHeight) {
        Button button = (Button) root.lookup(id);
        if (button != null) {
            button.setLayoutX(layoutX);
            button.setLayoutY(layoutY);
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight);
        }
    }

    private static void setTextLayout(Parent root, String id, double layoutX, double layoutY) {
        Text text = (Text) root.lookup(id);
        if (text != null) {
            text.setLayoutX(layoutX);
            text.setLayoutY(layoutY);
        }
    }

    private static void setLabelLayout(Parent root, String id, double layoutX, double layoutY) {
        Label label = (Label) root.lookup(id);
        if (label != null) {
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
        }
    }

    private static void setComboBoxLayout(Parent root, String id, double layoutX, double layoutY, double prefWidth, double prefHeight) {
        ComboBox<?> comboBox = (ComboBox<?>) root.lookup(id);
        if (comboBox != null) {
            comboBox.setLayoutX(layoutX);
            comboBox.setLayoutY(layoutY);
            comboBox.setPrefWidth(prefWidth);
            comboBox.setPrefHeight(prefHeight);
        }
    }
}
