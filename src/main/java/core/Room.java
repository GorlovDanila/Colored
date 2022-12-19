package core;

import gui.controller.WaitingCreatorController;
import protocols.MessagePacket;

import java.util.List;

public class Room implements Runnable {
    private final int id;
    private static List<Player> players;

    private int playersCount;

//    public static boolean isGameActive = false;

    public static List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Room(int id, List<Player> players, int playersCount) {
        this.id = id;
        this.players = players;
        this.playersCount = playersCount;
    }

    @Override
    public void run() {

//        for (int i = 0; i < playersCount; i++) {
//            //players.get(i) = new Player(server);
//            players.add(new Player(server));
//            players.get(i).writeObject("Ждём других игроков", 4, 5, 2);
//        }
        System.out.println(WaitingCreatorController.gameStartFlag);
//        if (WaitingCreatorController.gameStartFlag) {
            if (playersCount == players.size()) {

                System.out.println("Game started...\n");
                for (Player player : players) {
                    player.writeObject("Game started...", 4, 5, 2);
                }
                GameLogic logic = new GameLogic();
//                isGameActive = true;
                logic.setCorrectWord(currentWord());

                int idOfDrawer = setRoles(players, 0);

                for (Player player : players) {
                    if (player.getRole().equals("Drawer")) {
                        player.writeObject("Вы рисующий", MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
                        player.writeObject(logic.getCorrectWord(), 4, 5, 3);
                    } else {
                        player.writeObject("Вы угадывающий", 4, 5, 2);
                    }
                }

                String board = "";
                for (Player player : players) {
                    if (player.getRole().equals("Drawer")) {
                        board = (String) player.readObject(1);
                    }
                }

                for (Player player : players) {
                    if (!player.getRole().equals("Drawer")) {
                        player.writeObject(board, 4, 5, 2);
                    }
                }

                int countOfGuessed = 0;
                //читаем ответ игрока
                for (Player player : players) {
                    if (!player.getRole().equals("Drawer")) {
                        String word = (String) player.readObject(1);
                        if (logic.equalsWords(word)) {
                            countOfGuessed++;
                            player.writeObject("Вы угадали", 4, 5, 2);
                        } else {
                            player.writeObject("Вы не угадали", 4, 5, 2);
                        }
                    }
                }

                players.get(idOfDrawer).writeObject("Угадало " + countOfGuessed + " игроков", 4, 5, 2);
            }
        }
//    }

    private static int setRoles(List<Player> players, int id) {

        int idOfDrawer = -1;
        players.get(id).setRole("Guesser");
        if (id + 1 < players.size()) {
            players.get(id + 1).setRole("Drawer");
            idOfDrawer = id + 1;
        } else {
            players.get(0).setRole("Drawer");
            idOfDrawer = 0;
        }

        for (Player player : players) {
            if (!player.getRole().equals("Drawer")) {
                player.setRole("Guesser");
            }
        }

        return idOfDrawer;
    }


    public static void redrawWindow() {

    }

    private static String currentWord() {
        WordsRepository wordsRepository = new WordsRepository();
        List<String> words = wordsRepository.getWords();
        int max = words.size() - 1;
        int position = (int) (Math.random() * ++max);
        return words.get(position);
    }
}
