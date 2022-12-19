package server;

import client.PlayerClient;
import core.Player;
import core.Room;
import gui.controller.WaitingCreatorController;
import gui.controller.WaitingJoinerController;
import gui.scene.GuesserScene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<String> names = new ArrayList<>();
    public static boolean isGameActive = false;
    public static void main(String[] args) {

        int countClients = 2;

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");
            while (true) {
                List<Player> players = new ArrayList<>();
                List<Room> rooms = new ArrayList<>();

                Room room = new Room(0, players, 2);
                rooms.add(room);
//                PlayerClient client = new PlayerClient("127.0.0.1");
//                client.start();
//                PlayerClient client1 = new PlayerClient("127.0.0.1");
                for (int i = 0; i < 2; i++) {
                    //players.get(i) = new Player(server);
                    players.add(new Player(server));
                    players.get(i).setName((String) players.get(i).readObject(1));
                    players.get(i).setIdOfRoom((String) players.get(i).readObject(2));
//                    players.get(i).setName(names.get(i));
                    players.get(i).writeObject("Ждём других игроков", 4, 5, 2);
//                    client1.start();
                }
                System.out.println(players);
                room.setPlayers(players);
                players = new ArrayList<>();
                if(WaitingCreatorController.gameStartFlag) {
                    isGameActive = true;
                    room.run();
                }
            }
//            while (true) {
//                Common common = new Common(server);
//                new Thread(() -> {
//                    Object request = common.readObject(1);
//                    System.out.println("Request: " + request.toString());
//                    String response = (int) (Math.random() * 30 - 10) + "";
//                    try {
//                        Thread.sleep(4000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    common.writeObject(response, 4, 5, 2);
//                    System.out.println("Response: " + response);
//                    try { common.close();} catch (IOException e) {}
//                }).start();
//                server.accept();
//            }
//            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void startGame(Stage stage) {
        GuesserScene.display(stage, 0, "", 1080, 600);
    }
}

