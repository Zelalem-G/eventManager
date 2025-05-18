package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/event_management_system";
        String user = "root";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }

}
