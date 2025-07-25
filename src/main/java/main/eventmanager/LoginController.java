package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {
    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private TextField usernameTextfield;


        public void onSignup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onLogin(ActionEvent event) throws IOException {
          String username = usernameTextfield.getText();
          String password = passwordTextfield.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Please fill in all fields.");
            return;
        }

        AuthService.LoginResult result = AuthService.authenticate(username, password);

        switch (result) {
            case SUCCESS:
                SceneController.changeScene(event, "home.fxml", "Home", Session.getUsername(), Session.getRole());
                break;

            case USER_NOT_FOUND:
                errorMessageLabel.setText("Username not found.");
                break;

            case WRONG_PASSWORD:
                errorMessageLabel.setText("Incorrect password.");
                break;

            case ERROR:
                errorMessageLabel.setText("An error occurred. Please try again.");
                break;
        }

    }

}