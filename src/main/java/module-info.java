module main.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens main.eventmanager to javafx.fxml;
    opens model to javafx.base;
    exports main.eventmanager;
}