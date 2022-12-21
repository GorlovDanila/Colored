package gui.controller;

import gui.scene.AuthScene;
import gui.scene.DrawerScene;
import gui.scene.GuesserScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import protocols.MessagePacket;
import server.Server;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WaitingCreatorController {
    @FXML
    public Label connectedLabel;
    @FXML
    public Label creatorLabel;
    @FXML
    public Label lobbyId;

    public static boolean nextSceneFlag = false;

    public static CountDownLatch latch = new CountDownLatch(1);

    @FXML
    public void startAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = (Stage) connectedLabel.getScene().getWindow();
//        AuthController.client.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//        if (AuthController.client.readPacket().equals("SUBTYPE_START_GAME")) {
        int count = 0;
        AuthController.client.start();
        AuthController.client.getGameThread().writeObject(AuthController.client.getName(), 4, 5, 1);
        AuthController.client.getGameThread().writeObject(AuthController.client.getIdOfRoom(), 4, 5, 2);
//        writeObject(client.getName(), 4, 5, 1);
//        writeObject(client.getIdOfRoom(), 4, 5, 2);
//        Object response = AuthController.client.getGameThread().readObject(2);
//        System.out.println(response);
//        Thread.sleep(10000);
        AuthController.client.getGameThread().writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//        CountDownLatch latch = new CountDownLatch(1);
//        altch.await();
//        latch.countDown();
        count = 0;
        while (AuthController.client.getRole() == null) {
            Thread.sleep(1000);
        }
        System.out.println(1);
        String packet = AuthController.client.getGameThread().readPacket();
        System.out.println(packet);
        if (packet.equals("SUBTYPE_START_ROUND")) {
            if (AuthController.client.getRole().equals("Drawer")) {
//            Server.startGame(stage);
                DrawerScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
                        1080, 600);
                nextSceneFlag = true;
//
            } else {
//            Server.startGame(stage);
                GuesserScene.display(stage, Integer.parseInt(lobbyId.getText()), creatorLabel.getText(),
                        1080, 600);
                nextSceneFlag = true;
//                    gameStartFlag = true;
            }
        }
    }
//    }
}
