module com.snust.tetrij {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;
    requires org.json;


    opens com.snust.tetrij to javafx.fxml;
    exports com.snust.tetrij;
}