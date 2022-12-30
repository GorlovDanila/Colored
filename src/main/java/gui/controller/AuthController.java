package gui.controller;

import client.PlayerClient;
import gui.scene.AlertBox;
import gui.scene.WaitingCreatorScene;
import gui.scene.WaitingJoinerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;

public class AuthController {
    public static PlayerClient client;
    @FXML
    public TextField newLobbyInput;
    @FXML
    public TextField joinLobbyInput;
    @FXML
    public TextField nameInput;
    @FXML
    public TextField membersInput;

    @FXML
    public void joinLobbyAction(ActionEvent actionEvent) {
        if (isInt(joinLobbyInput)) {
            Stage stage = (Stage) joinLobbyInput.getScene().getWindow();
            client = new PlayerClient("127.0.0.1");
            client.setName(nameInput.getText());
            client.setIdOfRoom(Integer.parseInt(joinLobbyInput.getText()));
            client.setLobbyCreatorFlag(false);
            client.setCountPlayers(-1);
            try {
                WaitingJoinerScene.display(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void newLobbyAction(ActionEvent actionEvent) {
        if (isInt(newLobbyInput)) {
            Stage stage = (Stage) newLobbyInput.getScene().getWindow();
            Server.names.add(nameInput.getText());
            client = new PlayerClient("127.0.0.1");
            client.setName(nameInput.getText());
            client.setIdOfRoom(Integer.parseInt(newLobbyInput.getText()));
            client.setLobbyCreatorFlag(true);
            client.setCountPlayers(Integer.parseInt(membersInput.getText()));
            try {
                WaitingCreatorScene.display(stage);
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
