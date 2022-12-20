package server;

import client.PlayerClient;
import core.Player;
import core.Room;
import gui.controller.WaitingCreatorController;
import gui.controller.WaitingJoinerController;
import gui.scene.GuesserScene;
import javafx.stage.Stage;
import protocols.MessagePacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public static List<String> names = new ArrayList<>();
    public static boolean isGameActive = false;

    public static void main(String[] args) {

        int countClients = 2;
        Timer timer = new Timer();
        int count = 0;

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");
            while (true) {
            List<Player> players = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();

            Room room = new Room(0, players, 2);
            rooms.add(room);

//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    System.out.println(1);
//                    int count = 0;
//                    for (Player player : players) {
//                        if (!player.readPacket().equals("")) {
//                            if (player.readPacket().equals("SUBTYPE_START_GAME")) {
//                                count++;
//                            }
//                        }
//                    }
//                    if (count == countClients) {
//                        for (Player player : players) {
//                            player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                        }
//                        isGameActive = true;
//                        room.run();
//                    }
//                }
//            }, 0, 1000);

            for (int i = 0; i < 2; i++) {
                //players.get(i) = new Player(server);
                players.add(new Player(server));
                players.get(i).setName((String) players.get(i).readObject(1));
                players.get(i).setIdOfRoom((String) players.get(i).readObject(2));
                players.get(i).writeObject("Ждём других игроков", 4, 5, 2);
            }
                System.out.println(players);
                room.setPlayers(players);

                for (int i = 0; i < 2; i++) {
                    String subtype = players.get(i).readPacket();
                    if (!subtype.equals("")) {
//                        String subtype = players.get(i).readPacket();
//                        System.out.println(subtype);
                        if (subtype.equals("SUBTYPE_START_GAME")) {
//                            System.out.println(1);
                            count++;
                        }
                    }
                }
                System.out.println(count);
                if (count == countClients) {
//                    for (int i = 0; i < 2; i++) {
//                        players.get(i).writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                    }
                    isGameActive = true;
                    room.run();
                }


//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println(1);
//                    int count = 0;
//                    for (Player player : players) {
//                        if (!player.readPacket().equals("")) {
//                            if (player.readPacket().equals("SUBTYPE_START_GAME")) {
//                                count++;
//                            }
//                        }
//                    }
//                    if (count == countClients) {
//                        for (Player player : players) {
//                            player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                        }
//                        isGameActive = true;
//                        room.run();
//                    }
//                    }
//                }, 0, 1000);
//                players = new ArrayList<>();
            //В ЗАВИСИМОСТИ ОТ ТИПА ПАКЕТА НАЧИНАЕМ
//                long lastExecution = System.currentTimeMillis();
//                while (true) {
//                    if (System.currentTimeMillis() - lastExecution >= 1000) {
//                        System.out.println(1);
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println(1);
//                        int count = 0;
//                        for (Player player : players) {
//                            if (!player.readPacket().equals("")) {
//                                if (player.readPacket().equals("SUBTYPE_START_GAME")) {
//                                    count++;
//                                }
//                            }
//                        }
//                        if (count == countClients) {
//                            for (Player player : players) {
//                                player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                            }
//                            isGameActive = true;
//                            room.run();
//                        }
//                    }
//                }, 0, 1000);
//                try {
//                    Thread.sleep(10000); //Main thread sleep for 10 seconds to finish timer.
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    timer.cancel();
//                }
//                        int count = 0;
//                        for (Player player : players) {
//                            if (!player.readPacket().equals("")) {
//                                if (player.readPacket().equals("SUBTYPE_START_GAME")) {
//                                    count++;
//                                }
//                            }
//                        }
//                        if (count == countClients) {
//                            for (Player player : players) {
//                                player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                            }
//                            isGameActive = true;
//                            room.run();
//                        }
//            }
//                        lastExecution = System.currentTimeMillis();
//                }
//            }
//                if(WaitingCreatorController.gameStartFlag) {
//                    isGameActive = true;
//                    room.run();
//                }
//
//            int count = 0;
//            for (Player player : players) {
//                if (!player.readPacket().equals("")) {
//                    if (player.readPacket().equals("SUBTYPE_START_GAME")) {
//                        count++;
//                    }
//                }
//            }
//            if (count == countClients) {
//                for (Player player : players) {
//                    player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);
//                }
//                isGameActive = true;
//                room.run();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void startGame(Stage stage) {
        GuesserScene.display(stage, 0, "", 1080, 600);
    }
}

