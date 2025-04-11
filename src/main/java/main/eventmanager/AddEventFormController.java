package main.eventmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddEventFormController {
    @FXML
    private Label registerResultLabel;

    public void onRegisterEvent(ActionEvent event){
        registerResultLabel.setText("Event Succsesfully registerd");
    }
}
