package com.snust.tetrij;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SetResolution {
    public static void setStartMenu600x800(Parent root) {
        ImageView myImageView = (ImageView) root.lookup("#Tetrij");
        if (myImageView != null) {
            myImageView.setFitHeight(150.0);
            myImageView.setFitWidth(400.0);
            myImageView.setLayoutX(200.0);
            myImageView.setLayoutY(50.0);
        }

        Button btn1 = (Button) root.lookup("#startBtn1");
        if (btn1 != null) {
            btn1.setPrefWidth(220.0);
            btn1.setPrefHeight(60.0);
            btn1.setLayoutX(270.0);
            btn1.setLayoutY(180.0);
        }

        Button btn2 = (Button) root.lookup("#startBtn2");
        if (btn2 != null) {
            btn2.setPrefWidth(220.0);
            btn2.setPrefHeight(60.0);
            btn2.setLayoutX(270.0);
            btn2.setLayoutY(250.0);
        }

        Button btn3 = (Button) root.lookup("#startBtn3");
        if (btn3 != null) {
            btn3.setPrefWidth(220.0);
            btn3.setPrefHeight(60.0);
            btn3.setLayoutX(270.0);
            btn3.setLayoutY(320.0);
        }

        Button btn4 = (Button) root.lookup("#startBtn4");
        if (btn4 != null) {
            btn4.setPrefWidth(220.0);
            btn4.setPrefHeight(60.0);
            btn4.setLayoutX(270.0);
            btn4.setLayoutY(390.0);
        }

        Text textUp = (Text) root.lookup("#upText");
        if (textUp != null) {
            textUp.setLayoutX(550.0);
            textUp.setLayoutY(600.0);
        }

        Text textDown = (Text) root.lookup("#downText");
        if (textDown != null) {
            textDown.setLayoutX(550.0);
            textDown.setLayoutY(630.0);
        }
    }

    public static void setStartMenu800x1000(Parent root) {
        ImageView myImageView = (ImageView) root.lookup("#Tetrij");
        if (myImageView != null) {
            myImageView.setFitHeight(150.0);
            myImageView.setFitWidth(400.0);
            myImageView.setLayoutX(200.0);
            myImageView.setLayoutY(50.0);
        }

        Button btn1 = (Button) root.lookup("#startBtn1");
        if (btn1 != null) {
            btn1.setPrefWidth(250.0);
            btn1.setPrefHeight(90.0);
            btn1.setLayoutX(350.0);
            btn1.setLayoutY(230.0);
        }

        Button btn2 = (Button) root.lookup("#startBtn2");
        if (btn2 != null) {
            btn2.setPrefWidth(250.0);
            btn2.setPrefHeight(90.0);
            btn2.setLayoutX(350.0);
            btn2.setLayoutY(330.0);
        }

        Button btn3 = (Button) root.lookup("#startBtn3");
        if (btn3 != null) {
            btn3.setPrefWidth(250.0);
            btn3.setPrefHeight(90.0);
            btn3.setLayoutX(350.0);
            btn3.setLayoutY(430.0);
        }

        Button btn4 = (Button) root.lookup("#startBtn4");
        if (btn4 != null) {
            btn4.setPrefWidth(250.0);
            btn4.setPrefHeight(90.0);
            btn4.setLayoutX(350.0);
            btn4.setLayoutY(530.0);
        }
        Text textUp = (Text) root.lookup("#upText");
        if (textUp != null) {
            textUp.setLayoutX(550.0);
            textUp.setLayoutY(600.0);
        }

        Text textDown = (Text) root.lookup("#downText");
        if (textDown != null) {
            textDown.setLayoutX(550.0);
            textDown.setLayoutY(630.0);
        }
    }
}
