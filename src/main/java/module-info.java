module com.snust.tetrij {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    requires jdk.xml.dom;
    requires org.reflections;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.junit.platform.engine;
    requires org.junit.platform.launcher;
    requires com.google.common;
    requires org.junit.jupiter.engine;
    exports com.snust.tetrij to reflections, org.junit.jupiter.api, org.junit.platform.commons, junit;


    opens com.snust.tetrij to javafx.fxml;
    exports com.snust.tetrij.tetromino;
    opens com.snust.tetrij.tetromino to javafx.fxml;
    exports com.snust.tetrij.GameScene;
    opens com.snust.tetrij.GameScene to javafx.fxml;
    exports com.snust.tetrij.Controller;
    opens com.snust.tetrij.Controller to javafx.fxml;
    exports com.snust.tetrij.GameScene.GameSceneSingle;
    opens com.snust.tetrij.GameScene.GameSceneSingle to javafx.fxml;
//    exports com.snust.tetrij.UnitTest;


}