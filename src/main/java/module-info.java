module main.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens main.eventmanager to javafx.fxml;
    exports main.eventmanager;
}