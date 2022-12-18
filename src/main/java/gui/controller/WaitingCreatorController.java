package gui.controller;

import gui.scene.DrawerScene;
import gui.scene.GuesserScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WaitingCreatorController {
    @FXML
    public Label connectedLabel;
    @FXML
    public Label creatorLabel;
    @FXML
    public Label lobbyId;

    @FXML
    public void startAction(ActionEvent actionEvent) {
        Stage stage = (Stage) connectedLabel.getScene().getWindow();
        DrawerScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
                1080, 600);
    }
}
