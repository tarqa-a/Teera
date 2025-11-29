module com.teera.startpoint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.desktop;
    requires javafx.graphics;
    requires java.logging;
    requires org.fxmisc.richtext;
    requires reactfx;

    opens com.teera.startpoint to javafx.fxml;
    exports com.teera.startpoint;
}