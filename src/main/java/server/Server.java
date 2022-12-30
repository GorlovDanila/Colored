package server;

import com.google.gson.Gson;
import core.Player;
import core.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<String> names = new ArrayList<>();
    public static boolean isGameActive = false;

    static List<Room> rooms = new ArrayList<>();

    private static boolean flagExist = false;
    private static boolean flagNotExist = false;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");
            Gson gson = new Gson();
            while (true) {
                Player player = new Player(server);
                player.setName(gson.fromJson((String) player.readObject(1), String.class));
                player.setIdOfRoom(gson.fromJson((String) player.readObject(2), Integer.class));
                player.setCountPlayers(gson.fromJson((String) player.readObject(3), Integer.class));
                player.setLobbyCreatorFlag(gson.fromJson((String) player.readObject(4), Boolean.class));
                checkingRoomExist(player);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static void checkingRoomExist(Player player) {
        boolean roomExist = false;
        int roomPosition = -1;
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId() == player.getIdOfRoom()) {
                roomExist = true;
                roomPosition = i;
                break;
            }
        }
        if (player.isLobbyCreatorFlag()) {
            if (roomExist) {
                //выкидывать алерт бокс что такая комната уже существует
//                AlertBox.display("Ошибка", "Комната с таким id уже существует");
            }
            if (!roomExist) {
                flagExist = true;
                Room room = new Room(player.getIdOfRoom(), player.getCountPlayers());
                players = room.getPlayers();
                players.add(player);
                room.setPlayers(players);
                rooms.add(room);
                checkCountPlayersInRoom(players, player.getIdOfRoom());
            }
        } else {
            if (roomExist) {
                flagNotExist = true;
                Room room = rooms.get(roomPosition);
                players = room.getPlayers();
                players.add(player);
                room.setPlayers(players);
                checkCountPlayersInRoom(players, player.getIdOfRoom());
            }
            if (!roomExist) {
                //выкидываем алерт бокс что комната к которой хочет присоединиться не существует
//                AlertBox.display("Ошибка", );
            }
        }
    }

    private static void checkCountPlayersInRoom(List<Player> players, int roomId) {
        int count = 0;
        for (Room room : rooms) {
            if (roomId == room.getId() && players.size() == room.getPlayersCount()) {
                for (Player player : players) {
                    player.writeObject("Ждём готовности игроков", 4, 5, 2);
                }
                for (Player player : players) {
                    String subtype = player.readPacket();
                    if (!subtype.equals("")) {
                        if (subtype.equals("SUBTYPE_START_GAME")) {
                            count++;
                        }
                    }
                }
                if (count == players.size()) {
                    isGameActive = true;
                    new Thread(room).start();
                }
            }
        }
    }
}