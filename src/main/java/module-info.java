module com.snust.tetrij {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.snust.tetrij to javafx.fxml;
    exports com.snust.tetrij;

}