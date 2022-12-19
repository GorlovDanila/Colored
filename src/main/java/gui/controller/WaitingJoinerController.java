package gui.controller;

import core.Player;
import core.Room;
import gui.scene.DrawerScene;
import gui.scene.GuesserScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;
import java.util.List;

public class WaitingJoinerController {
    @FXML
    public Label connectedLabel;
    @FXML
    public Label creatorLabel;
    @FXML
    public Label lobbyId;

    @FXML
    public void startAction(ActionEvent actionEvent) {
        Stage stage = (Stage) connectedLabel.getScene().getWindow();
        if (AuthController.client.getRole().equals("Drawer")) {
//            Server.startGame(stage);
            //клиент отправляет сообщение на сервер с типом старт
            //сюда приходит ответ от сервака,
            DrawerScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
                    1080, 600);
//            gameStartFlag = true;
        } else {
            Server.startGame(stage);
            GuesserScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
                    1080, 600);
//            gameStartFlag = true;
        }
    }

//    @FXML
//    public void initialize() throws IOException {
//        Stage stage = (Stage) connectedLabel.getScene().getWindow();
//        while (!Server.isGameActive) {
//            continue;
//        }
//        GuesserScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
//                1080, 600);
//    }
}
