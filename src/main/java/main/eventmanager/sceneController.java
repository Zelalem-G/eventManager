package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneController {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
            Parent root = loader.load();

            if (username != null && role != null) {
                // Pass user data to the controller
                HomePageController controller = loader.getController();
                controller.setUserInformation(username, role); // Or pass only what you need
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Scene Error", "Could not load the scene.");
        }
    }
}
