package gui.controller;

import gui.scene.AlertBox;
import gui.scene.WaitingCreatorScene;
import gui.scene.WaitingJoinerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {
    @FXML
    public TextField newLobbyInput;
    @FXML
    public TextField joinLobbyInput;
    @FXML
    public TextField nameInput;
    @FXML
    public int newLobby;

    @FXML
    public void joinLobbyAction(ActionEvent actionEvent) {
        if (isInt(joinLobbyInput)) {
            Stage stage = (Stage) joinLobbyInput.getScene().getWindow();
            try {
                WaitingJoinerScene.display(stage, 1080, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void newLobbyAction(ActionEvent actionEvent) {
        if (isInt(newLobbyInput)) {
            Stage stage = (Stage) newLobbyInput.getScene().getWindow();
            try {
                WaitingCreatorScene.display(stage, 1080, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isInt(TextField input) {
        try {
            int num = Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException e) {
            AlertBox.display("Ошибка","Нужно ввести именно число");
            return false;
        }
    }
}
