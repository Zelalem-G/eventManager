package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignupController {
    private Scene scene;
    private Parent root;
    private Stage stage;
    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField passwordTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField fullNameTextfield;
    @FXML
    private RadioButton userRadioBtn;
    @FXML
    private RadioButton adminRadioBtn;

    public String selectedRole() {
        String selectedRole = "";
        if (adminRadioBtn.isSelected()) {
            selectedRole = "admin";
        } else if (userRadioBtn.isSelected()) {
            selectedRole = "user";
        }
        return selectedRole;
    }

    public void onSignup(ActionEvent event) throws IOException {
        String username = usernameTextfield.getText().trim();
        String password = passwordTextfield.getText().trim();
        String email = emailTextfield.getText().trim();
        String fullName = fullNameTextfield.getText().trim();
        String role = selectedRole();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullName.isEmpty() || role == null) {
            errorMessageLabel.setText("Please fill in all fields.");
            return;
        }

        AuthService.SignupResult result = AuthService.registerUser(username, password, email , fullName, role);

        switch (result) {
            case SUCCESS:
                Session.setSession(username , role);
                SceneController.changeScene(event, "homePage.fxml", "Home", username, role);
                return;

            case USERNAME_TAKEN:
                errorMessageLabel.setText("Username already taken. Try another.");
                break;

            case ERROR:
                errorMessageLabel.setText("Something went wrong. Please try again later.");
                break;
        }

    }

    public void onLogin(ActionEvent event) throws IOException {
//        errorMessageLabel.setText("Login button clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}