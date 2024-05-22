module com.snust.tetrij {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    requires junit;
    requires jdk.xml.dom;


    opens com.snust.tetrij to javafx.fxml;
    exports com.snust.tetrij;
    exports com.snust.tetrij.tetromino;
    opens com.snust.tetrij.tetromino to javafx.fxml;
    exports com.snust.tetrij.GameScene;
    opens com.snust.tetrij.GameScene to javafx.fxml;
    exports com.snust.tetrij.Controller;
    opens com.snust.tetrij.Controller to javafx.fxml;
    exports com.snust.tetrij.GameScene.GameSceneSingle;
    opens com.snust.tetrij.GameScene.GameSceneSingle to javafx.fxml;

}