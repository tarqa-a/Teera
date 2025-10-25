module com.teera.startpoint.teera {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teera.startpoint.teera to javafx.fxml;
    exports com.teera.startpoint.teera;
}