package server;

import core.Player;
import core.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {

        int countClients = 2;

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");
            while (true) {
                List<Player> players = new ArrayList<>();
                List<Room> rooms = new ArrayList<>();

                Room room = new Room(0, players, 2);
                rooms.add(room);

                for (int i = 0; i < 2; i++) {
                    //players.get(i) = new Player(server);
                    players.add(new Player(server));
                    players.get(i).writeObject("Ждём других игроков", 4, 5, 2);
                }
                room.setPlayers(players);
                players = new ArrayList<>();
                room.run();
            }

//            System.out.println("Game started...\n");
//            for (int i = 0; i < 2; i++) {
//                players.get(i).writeObject("Game started...", 4, 5, 2);
//            }

            //ROOOOOOOOOOOOOOOOOOOOOOM
//            Room room = new Room(0, players);
//            new Thread(() -> {
//
//            }).start();

//            GameLogic logic = new GameLogic();
//            logic.setCorrectWord(currentWord());
//
//            int idOfDrawer = setRoles(players, 0);

//            for (Player player : players) {
//                if (player.getRole().equals("Drawer")) {
//                    player.writeObject("Вы рисующий", MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
//                    player.writeObject(logic.getCorrectWord(), 4, 5, 3);
//                } else {
//                    player.writeObject("Вы угадывающий", 4, 5, 2);
//                }
//            }

//            String board = "";
//            for (Player player : players) {
//                if (player.getRole().equals("Drawer")) {
//                    board = (String) player.readObject(1);
//                }
//            }
//
//            for (Player player : players) {
//                if (!player.getRole().equals("Drawer")) {
//                    player.writeObject(board, 4, 5, 2);
//                }
//            }
//
//            int countOfGuessed = 0;
//            //читаем ответ игрока
//            for (Player player : players) {
//                if (!player.getRole().equals("Drawer")) {
//                    String word = (String) player.readObject(1);
//                    if (logic.equalsWords(word)) {
//                        countOfGuessed++;
//                        player.writeObject("Вы угадали", 4, 5, 2);
//                    } else {
//                        player.writeObject("Вы не угадали", 4, 5, 2);
//                    }
//                }
//            }
//
//            players.get(idOfDrawer).writeObject("Угадало " + countOfGuessed + " игроков", 4, 5, 2);

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
}

