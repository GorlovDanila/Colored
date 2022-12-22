package server;

import core.Player;
import core.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<String> names = new ArrayList<>();
    public static boolean isGameActive = false;

    public static void main(String[] args) {

        int countClients = 2;
//        Timer timer = new Timer();
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
                        if (subtype.equals("SUBTYPE_START_GAME")) {
                            count++;
                        }
                    }
                }
                System.out.println(count);
                if (count == countClients) {
                    isGameActive = true;
                    room.run();
                }

                //В ЗАВИСИМОСТИ ОТ ТИПА ПАКЕТА НАЧИНАЕМ
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

