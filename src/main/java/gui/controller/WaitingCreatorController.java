package gui.controller;

import gui.scene.AuthScene;
import gui.scene.DrawerScene;
import gui.scene.GuesserScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.Server;

public class WaitingCreatorController {
    @FXML
    public Label connectedLabel;
    @FXML
    public Label creatorLabel;
    @FXML
    public Label lobbyId;

    public static boolean gameStartFlag = false;

    @FXML
    public void startAction(ActionEvent actionEvent) {

//        AuthController.client.writeObject();
        // TODO: отправить комнате сообщение о том, что клиентам пора новые сцены создавать

//        Stage stage = (Stage) connectedLabel.getScene().getWindow();
//        if (AuthController.client.getRole() != null) {
//            if (AuthController.client.getRole().equals("Drawer")) {
//
////                Server.startGame(stage);
//                DrawerScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
//                        1080, 600);
//            } else {
////                Server.startGame(stage);
//                GuesserScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
//                        1080, 600);
//            }
//        }
    }
}
