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

//    public static void changeScene(ActionEvent event, String fxmlFile,String title , String username, String role){
//        Parent root = null;
//
//        if(username != null ){
//            try{
//                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
//                root = loader.load();
//
//                HomePageController homePageController = loader.getController();
//                homePageController.setUserInformation(username);
//
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }else {
//            try{
//                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//
//        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root, 600, 400));
//        stage.setTitle(title);
//
//    }
}
