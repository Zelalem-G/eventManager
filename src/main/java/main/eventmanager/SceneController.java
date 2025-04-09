package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role) {

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
            Parent root = loader.load(); // this loads the scene AND wires up the controller

            HomePageController controller = loader.getController();

            controller.setUserInformation(username, role);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Scene Error", "Could not load the scene.");
        }
    }
}
