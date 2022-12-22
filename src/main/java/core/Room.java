package core;

import com.google.gson.Gson;
import protocols.MessagePacket;

import java.io.File;
import java.util.List;

public class Room implements Runnable {
    private final int id;
    private List<Player> players;

    public static GameLogic logic = new GameLogic();
    private int playersCount;

    public static boolean getBoardFlag = false;

//    public static boolean isGameActive = false;

    public List<Player> getPlayers() {
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
//ЛЮДИ ОБЩАЮТСЯ ЧЕРЕЗ СЕРВАК

        //ПЕРЕПИСАТЬ ВСЁ НА МЕТОДЫ
        if (playersCount == players.size()) {

//            GameLogic logic = new GameLogic();
            setCorrectWord(logic);

            startGame(players);

            sendPacket(players, MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);

            setRoles(players);

            notificationPlayersAboutRoles(players, logic);
            getPacket(players);
            if (allPlayersReady(players)) {
                sendCorrectWord(players, logic);

                while (logic.isRoundActive()) {
                    File board = getBoard(players);
                    notificationPlayersAboutBoard(players, board);
                    //notificationPlayerAboutGameResult(players, logic);
                }

//            notificationPlayersAboutBoard(players, board);
//                notificationPlayerAboutGameResult(players, logic);
//
            }
        }
    }


    private static void setRoles(List<Player> players) {
        int idOfDrawer = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole().equals("Drawer")) {
                idOfDrawer = i;
                break;
            }
        }
        if (idOfDrawer == -1) {
            players.get(0).setRole("Drawer");
        } else {
            players.get(idOfDrawer).setRole("Guesser");
        }
        if (idOfDrawer + 1 < players.size()) {
            players.get(idOfDrawer + 1).setRole("Drawer");
        } else {
            players.get(0).setRole("Drawer");
        }
    }

    private static void notificationPlayersAboutRoles(List<Player> players, GameLogic gameLogic) {
        for (Player player : players) {
            if (player.getRole().equals("Drawer")) {
                player.writeObject("Вы рисующий", MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
//                player.writeObject(gameLogic.getCorrectWord(), 4, 5, 3);
            } else {
                player.writeObject("Вы угадывающий", 4, 5, 2);
            }
        }
    }

    private static void sendCorrectWord(List<Player> players, GameLogic gameLogic) {
        for (Player player : players) {
            if(player.getRole().equals("Drawer")) {
//                player.writeMessage(MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_CORRECT_WORD);
                player.writeObject(gameLogic.getCorrectWord(), MessagePacket.TYPE_META, MessagePacket.SUBTYPE_CORRECT_WORD, 3);
            }
        }
    }

    private static void notificationPlayersAboutBoard(List<Player> players, File path) {
        getBoardFlag = true;
        for (Player player : players) {
            if (!player.getRole().equals("Drawer")) {
                player.setGetBoardFlag(true);
//                getBoardFlag = true;
                player.writeObject(path, MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
            }
        }
    }

    private static void notificationPlayerAboutGameResult(List<Player> players, GameLogic gameLogic) {
        String nickGuessed = null;
        String idOfDrawer = null;
        Gson gson = new Gson();
        while (nickGuessed == null) {
            for (Player player : players) {
                if (!player.getRole().equals("Drawer")) {
                    String word = gson.fromJson((String) player.readObject(1), String.class);
                    System.out.println(word);
                    if (gameLogic.equalsWords(word)) {
                        player.writeObject("Вы угадали", 4, 5, 2);
                        nickGuessed = player.getName();
                        break;
                    } else {
                        player.writeObject("Вы не угадали", 4, 5, 2);
                    }
                }
            }
        }

        for (Player player : players) {
            if (player.getRole().equals("Drawer")) {
                player.writeObject(nickGuessed + " угадал", 4, 5, 2);
            }
        }
    }

    private static String currentWord() {
        WordsRepository wordsRepository = new WordsRepository();
        List<String> words = wordsRepository.getWords();
        int max = words.size() - 1;
        int position = (int) (Math.random() * ++max);
        return words.get(position);
    }

    private static void startGame(List<Player> players) {
        System.out.println("Game started...\n");
        for (Player player : players) {
            player.writeObject("Game started...", 4, 5, 2);
        }
    }

    private File getBoard(List<Player> players) {
        File board = null;
        for (Player player : players) {
            if (player.getRole().equals("Drawer")) {
                board = (File) player.readObject(5);
            }
        }
        return board;
    }

    private static void setCorrectWord(GameLogic gameLogic) {
        gameLogic.setCorrectWord(currentWord());
    }

    private static void sendPacket(List<Player> players, int type, int subtype) {
        for (Player player : players) {
            player.writeMessage(type, subtype);
        }
    }

    private static void getPacket(List<Player> players) {
        boolean startRoundFlag = true;
        boolean getBoard = false;
        for (Player player : players) {
            String subtype = player.readPacket();
            if (!subtype.equals("SUBTYPE_START_ROUND")) {
                startRoundFlag = false;
            }
            if(subtype.equals("SUBTYPE_JSON")) {
                getBoard = true;
            }
        }
        if (startRoundFlag) {
            for (Player player : players) {
                player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
            }
        }
//        if(getBoard) {
//
//        }
    }

    public static boolean allPlayersReady(List<Player> players) {
        boolean flag = true;
        for (Player player : players) {
            if(!player.readPacket().equals("SUBTYPE_START_ROUND")) {
                flag = false;
            }
        }
        return flag;
    }
}
